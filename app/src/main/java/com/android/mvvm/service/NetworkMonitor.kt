package com.android.mvvm.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.android.mvvm.App
import com.android.mvvm.util.Logger
import com.android.mvvm.core.constant.NetworkStatus
import com.android.mvvm.core.extension.getMainThread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NetworkMonitor(
        private val context: App,
) : KodeinAware {
    companion object {
        private const val TAG = "NetworkMonitor"
    }

    /**
     * The interval that we will ping url with
     */
    private val INTERVAL = 1000L * 60L * 15L

    /**
     * How long we should wait between 400-599 http status codes on http requests
     */
    private val HOLDTIME = 1000 * 5

    override val kodein: Kodein by kodein(context)
    private val quickHttpClient: OkHttpClient by instance(arg = 8L)
    val networkStatus = MutableLiveData<NetworkStatus>(NetworkStatus.Disconnected)
    val isWifi = MutableLiveData(false)
    val wifiInfo = MutableLiveData<WifiInfo>(null)

    private val PING_URL = "http://wifi.3xiaozhi.com"
    private var isNetworkUp = false

    //    var disconnectedTime: LocalDateTime = LocalDateTime.now()
    var disconnectedTime = System.currentTimeMillis()

    init {
        context.getMainThread {
            isWifi.observeForever {
                Logger.d(TAG, "isWifi=" + it)
                if (it && networkStatus.value != NetworkStatus.Disconnected) {
                    val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    wifiInfo.postValue(wm.connectionInfo)
                } else {
                    wifiInfo.postValue(null)
                }
            }
        }

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        Logger.d(TAG, "VERSION_CODES=" + Build.VERSION.SDK_INT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isNetworkUp = true
                    updateNetworkStatus(NetworkStatus.Connected)
                    val capabilities = cm.getNetworkCapabilities(network)
                    if (capabilities != null) {
                        updateWifiCapabilities(
                                capabilities.hasTransport(
                                        NetworkCapabilities.TRANSPORT_WIFI
                                )
                        )
                    }
                    startRepeatingTask()
                }

                override fun onCapabilitiesChanged(
                        network: Network,
                        networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    updateWifiCapabilities(
                            networkCapabilities.hasTransport(
                                    NetworkCapabilities.TRANSPORT_WIFI
                            )
                    )
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    updateNetworkLost()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    updateNetworkLost()
                    isNetworkUp = false
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    updateNetworkLost()
                    isNetworkUp = false
                }
            })
        } else {
            cm.registerNetworkCallback(
                    NetworkRequest.Builder().build(),
                    object : ConnectivityManager.NetworkCallback() {
                        // 在框架连接并声明可以使用新网络时调用
                        override fun onAvailable(network: Network) {
                            super.onAvailable(network)
                            // 网络已连接
                            isNetworkUp = true
                            updateNetworkStatus(NetworkStatus.Connected)
                            val capabilities = cm.getNetworkCapabilities(network)
                            if (capabilities != null) {
                                updateWifiCapabilities(
                                        capabilities.hasTransport(
                                                NetworkCapabilities.TRANSPORT_WIFI
                                        )
                                )
                            }
                            startRepeatingTask()
                        }

                        override fun onCapabilitiesChanged(
                                network: Network,
                                networkCapabilities: NetworkCapabilities
                        ) {
                            super.onCapabilitiesChanged(network, networkCapabilities)
                            updateWifiCapabilities(
                                    networkCapabilities.hasTransport(
                                            NetworkCapabilities.TRANSPORT_WIFI
                                    )
                            )
                        }

                        override fun onLosing(network: Network, maxMsToLive: Int) {
                            super.onLosing(network, maxMsToLive)
                            updateNetworkLost()
                        }

                        // 当网络断开连接或不再满足此请求或回调时调用
                        override fun onLost(network: Network) {
                            super.onLost(network)
                            // 网络已断开
                            updateNetworkLost()
                            isNetworkUp = false
                        }

                        override fun onUnavailable() {
                            super.onUnavailable()
                            updateNetworkLost()
                            isNetworkUp = false
                        }
                    }
            )
        }
    }

    private fun updateNetworkLost() {
        updateNetworkStatus(NetworkStatus.Disconnected)
    }

    private fun updateNetworkStatus(newStatus: NetworkStatus) {
        if (networkStatus.value != newStatus) {
            if (newStatus != NetworkStatus.Connected) {
                disconnectedTime = System.currentTimeMillis()
            }
            context.getMainThread {
                Logger.d(TAG, "NetworkStatus=" + newStatus)
                if (!isNetworkUp && newStatus == NetworkStatus.Disconnected) {
                    isWifi.postValue(false)
                }
                networkStatus.postValue(newStatus)
            }
        }
    }

    private fun updateWifiCapabilities(transportWifi: Boolean) {
        context.getMainThread {
            if (isWifi.value != transportWifi) {
                Logger.d(TAG, "isWifi=" + transportWifi)
                isWifi.postValue(transportWifi)
            }
        }
    }

    fun checkAvailableConnection(callback: (NetworkStatus) -> Unit = {}) {
        // if the OS has no network, then checking for urls will set the wrong message
        if (!isNetworkUp) {
            callback.invoke(NetworkStatus.Disconnected)
            return
        }
        GlobalScope.launch {
            if (quickPingURL(PING_URL) in 200..299) {
                updateNetworkStatus(NetworkStatus.Connected)
                callback.invoke(NetworkStatus.Connected)
            } else {
                if (isNetworkUp) {
                    updateNetworkStatus(NetworkStatus.ConnectedWifiNoData)
                    callback.invoke(NetworkStatus.ConnectedWifiNoData)
                } else {
                    updateNetworkStatus(NetworkStatus.Disconnected)
                    callback.invoke(NetworkStatus.Disconnected)
                }
            }
        }
    }

    var mHandler: Handler = Handler(Looper.getMainLooper())
    var mHandlerTask: Runnable = object : Runnable {
        override fun run() {
            checkAvailableConnection()
            mHandler.postDelayed(this, INTERVAL)
        }
    }

    fun startRepeatingTask() {
        mHandlerTask.run()
    }

    fun stopRepeatingTask() {
        mHandler.removeCallbacks(mHandlerTask)
    }

    var lastCheck = System.currentTimeMillis()
    private var currentStatus: NetworkStatus = NetworkStatus.Disconnected
    fun setResponseCode(code: Int) {
        if (code in 200..299 && currentStatus != NetworkStatus.Connected) {
            updateNetworkStatus(NetworkStatus.Connected)
        }

        if (code in 400..599 && isNetworkUp) {
            val diff = System.currentTimeMillis() - lastCheck
            if (diff >= HOLDTIME) {
                stopRepeatingTask()
                startRepeatingTask()
            }
        }
    }

    /**
     * "quick pings" a url using the quick client (8 second timeout)
     *
     * @param url - url to ping
     * @return status code of response. -1 if error/timeout
     */
    private fun quickPingURL(url: String): Int {
        Logger.i(TAG, "Starting quick ping for $url")
        if (url.contains("wifi.jinhengsw.com")) {
            lastCheck = System.currentTimeMillis()
        }
        return try {
            val response = quickHttpClient.newCall(
                    Request.Builder()
                            .url(url)
                            .build()
            ).execute()
            Logger.i(TAG, "Ping response code for $url was ${response.code()}")
            response.code()
        } catch (e: Exception) {
            Logger.d(TAG, "Ping to $url failed with $e")
            -1
        }
    }
}