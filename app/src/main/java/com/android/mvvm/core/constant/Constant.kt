package com.android.mvvm.core.constant

object Constant {
    const val DELAY_SHORT = 200L

    const val DELAY_MIDDLE = 500L

    const val DELAY_LONG = 1000L

    const val DELAY_TOO_LONG = 2000L

    const val ALPHA_NUM_REGEX_PATTERN = "^[a-zA-Z0-9]+$"

//    val navigationBarActivityList = mutableListOf<String>(
    val NAVIGATION_BAR_ACTIVITY_LIST = mutableListOf<String>(
//        WelcomeActivity::class.java.simpleName
    )
}

sealed class NetworkStatus(val value: Int) {
    object Connected : NetworkStatus(0)
    object ConnectedNoInternet : NetworkStatus(1)
    object ConnectedWifiNoData : NetworkStatus(2)
    object Disconnected : NetworkStatus(-1)
}

