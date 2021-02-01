package com.android.mvvm.web.interceptor

import android.content.Context
import android.os.Handler
import android.widget.Toast
import com.android.mvvm.BuildConfig
import com.android.mvvm.util.Logger
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import javax.net.ssl.SSLPeerUnverifiedException

class WebLogger(private val context: Context) : Interceptor {

    companion object {
        private const val TAG = "WebLogger"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        Logger.v(TAG, "Starting Intercept")
        val request = chain.request()

        val t1 = System.nanoTime()
        Logger.v(
            TAG, String.format(
                "Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()
            )
        )

        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            val code =
                when (e) {
                    is SocketTimeoutException -> -2
                    is SSLPeerUnverifiedException -> -1
                    else -> -9999
                }
            showResponseToast(code, request.url().toString())
            throw e
        }

        Logger.v(TAG, "Response Code: ${response.code()}")

        val t2 = System.nanoTime()
        Logger.v(
            TAG, String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )

        return response
    }

    private fun showResponseToast(code: Int, url: String) {
        if (BuildConfig.DEBUG) {
            val msg = String.format(
                "Received %d response for %s",
                code,
                url
            )
            context.getMainThread {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
    }


    /**
     * 给定上下文，获取主循环程序，以便我们可以在UI线程上发布消息
     */
    private fun Context.getMainThread(function: () -> Unit) {
        Handler(this.mainLooper).post(function)
    }

}