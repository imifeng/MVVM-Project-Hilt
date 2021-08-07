package com.android.mvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseActivity
import com.android.mvvm.core.constant.NetworkStatus
import com.android.mvvm.core.model.BaseProperties
import com.android.mvvm.core.view.TabItem
import com.android.mvvm.core.view.toTab
import com.android.mvvm.databinding.ActivityMainBinding
import com.android.mvvm.ui.test.FirstFragment
import com.android.mvvm.ui.test.SecondFragment
import com.android.mvvm.ui.test.TestFragment
import com.android.mvvm.ui.test.ThirdFragment
import com.android.mvvm.util.Logger

class MainActivity : BaseActivity() {
    override val baseProperties: BaseProperties =
        BaseProperties(layoutResource = R.layout.activity_main)

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.bind(viewParent) }

    private var firstFragment: FirstFragment? = null
    private var secondFragment: SecondFragment? = null
    private var thirdFragment: ThirdFragment? = null
    private var testFragment2: TestFragment? = null
    private var testFragment3: TestFragment? = null

    private var currentItem: TabItem? = null
    private var currentNetStatus: NetworkStatus? = null


    override fun init() {
        super.init()
        Logger.d(TAG, "init")
        networkMonitor.networkStatus.observe(this, Observer {
            if (it == NetworkStatus.Connected && currentNetStatus != it) {
                Logger.d(TAG, "networkStatus: $it")
                // 网络已连接
            }
            currentNetStatus = it
        })

        switchTabByPosition(TabItem.TAB_TYPE_FIRST)

        binding.tabLayout.onClickTab = {
            Logger.d(TAG, it.name)
            switchTabByPosition(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d(TAG, "onSaveInstanceState")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Clear the intent so that screen rotations don't cause the intent to be re-executed on.
        Logger.d(TAG, "onNewIntent")
        setIntent(Intent())
        intent?.let {
            val value = it.getIntExtra("TabItem", 0)
            switchTabByClick(value.toTab())
        }
    }

    private fun switchTabByClick(tab: TabItem) {
        currentItem = null
        binding.tabLayout.clickItem(tab)
    }

    fun gotoSecondFragment() {
        switchTabByClick(TabItem.TAB_TYPE_SECOND)
    }


    private fun switchTabByPosition(tabItem: TabItem) {
        if (currentItem == tabItem) {
            return
        }
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when (tabItem) {
            TabItem.TAB_TYPE_FIRST -> {
                firstFragment?.let {
                    transaction.show(it)
                } ?: run {
                    firstFragment = FirstFragment()
                    transaction.add(
                        R.id.container,
                        firstFragment!!,
                        FirstFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_SECOND -> {
                secondFragment?.let {
                    transaction.show(it)
                } ?: run {
                    secondFragment = SecondFragment()
                    transaction.add(
                        R.id.container,
                        secondFragment!!,
                        SecondFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_THIRD -> {
                thirdFragment?.let {
                    transaction.show(it)
                } ?: run {
                    thirdFragment = ThirdFragment()
                    transaction.add(
                        R.id.container,
                        thirdFragment!!,
                        ThirdFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_FOURTH -> {
                testFragment2?.let {
                    transaction.show(it)
                } ?: run {
                    testFragment2 = TestFragment()
                    transaction.add(
                        R.id.container,
                        testFragment2!!,
                        TestFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_FIFTH -> {
                testFragment3?.let {
                    transaction.show(it)
                } ?: run {
                    testFragment3 = TestFragment()
                    transaction.add(
                        R.id.container,
                        testFragment3!!,
                        TestFragment::class.java.simpleName
                    )
                }
            }
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commitAllowingStateLoss()
        currentItem = tabItem
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        firstFragment?.let {
            transaction.hide(it)
        }
        secondFragment?.let {
            transaction.hide(it)
        }
        thirdFragment?.let {
            transaction.hide(it)
        }
        testFragment2?.let {
            transaction.hide(it)
        }
        testFragment3?.let {
            transaction.hide(it)
        }
    }

    override fun onBackPressed() {
        if (currentItem != TabItem.TAB_TYPE_FIRST) {
            binding.tabLayout.clickItem(TabItem.TAB_TYPE_FIRST)
            return
        }
        super.onBackPressed()
    }
}
