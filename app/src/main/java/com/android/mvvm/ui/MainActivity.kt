package com.android.mvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.observe
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseActivity
import com.android.mvvm.core.constant.NetworkStatus
import com.android.mvvm.core.model.ActivityProperties
import com.android.mvvm.core.view.TabItem
import com.android.mvvm.core.view.toTab
import com.android.mvvm.ui.test.FirstFragment
import com.android.mvvm.ui.test.SecondFragment
import com.android.mvvm.ui.test.TestFragment
import com.android.mvvm.util.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val activityProperties: ActivityProperties = ActivityProperties(layoutResID = R.layout.activity_main)

    private var firstFragment: FirstFragment? = null
    private var secondFragment: SecondFragment? = null
    private var testFragment1: TestFragment? = null
    private var testFragment2: TestFragment? = null
    private var testFragment3: TestFragment? = null

    private var currentItem: TabItem? = null
    private var currentNetStatus: NetworkStatus? = null


    override fun init() {
        super.init()
        Logger.d(TAG, "init")
        networkMonitor.networkStatus.observe(this) {
            if (it == NetworkStatus.Connected && currentNetStatus != it) {
                Logger.d(TAG, "networkStatus: $it")
                // 网络已连接
            }
            currentNetStatus = it
        }

        switchTabByPosition(TabItem.TAB_TYPE_FIRST)

        tab_layout.onClickTab = {
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
        tab_layout.clickItem(tab)
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
                if (firstFragment != null) {
                    transaction.show(firstFragment!!)
                } else {
                    firstFragment = FirstFragment()
                    transaction.add(
                            R.id.container,
                            firstFragment!!,
                            FirstFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_SECOND -> {
                if (secondFragment != null) {
                    transaction.show(secondFragment!!)
                } else {
                    secondFragment = SecondFragment()
                    transaction.add(
                            R.id.container,
                            secondFragment!!,
                            SecondFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_THIRD -> {
                if (testFragment1 != null) {
                    transaction.show(testFragment1!!)
                } else {
                    testFragment1 = TestFragment()
                    transaction.add(
                            R.id.container,
                            testFragment1!!,
                            TestFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_FOURTH -> {
                if (testFragment2 != null) {
                    transaction.show(testFragment2!!)
                } else {
                    testFragment2 = TestFragment()
                    transaction.add(
                            R.id.container,
                            testFragment2!!,
                            TestFragment::class.java.simpleName
                    )
                }
            }
            TabItem.TAB_TYPE_FIFTH -> {
                if (testFragment3 != null) {
                    transaction.show(testFragment3!!)
                } else {
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
        testFragment1?.let {
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
            tab_layout.clickItem(TabItem.TAB_TYPE_FIRST)
            return
        }
        super.onBackPressed()
    }
}
