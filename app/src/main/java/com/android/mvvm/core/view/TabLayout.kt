package com.android.mvvm.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.android.mvvm.R
import com.android.mvvm.core.extension.getLayoutInflater
import com.android.mvvm.core.extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.view_main_tab.view.*

class TabLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var currentItem = TabItem.TAB_TYPE_FIRST
    private var clickItem = TabItem.TAB_TYPE_FIRST

    var onClickTab: ((tabItem: TabItem) -> Unit)? = null

    private val tabItems by lazy { mutableListOf<View>() }

    init {
        context.getLayoutInflater().inflate(R.layout.view_main_tab, this, true)
        //init Text
        tabItems.add(tv_first)
        tabItems.add(tv_second)
        tabItems.add(tv_third)
        tabItems.add(tv_fourth)
        tabItems.add(tv_fifth)

        clickItem()

        layout_first.setOnSingleClickListener {
            if (currentItem == TabItem.TAB_TYPE_FIRST) {
                return@setOnSingleClickListener
            }
            clickItem(TabItem.TAB_TYPE_FIRST)
        }

        layout_second.setOnSingleClickListener {
            if (currentItem == TabItem.TAB_TYPE_SECOND) {
                return@setOnSingleClickListener
            }
            clickItem(TabItem.TAB_TYPE_SECOND)
        }

        layout_third.setOnSingleClickListener {
            if (currentItem == TabItem.TAB_TYPE_THIRD) {
                return@setOnSingleClickListener
            }
            clickItem(TabItem.TAB_TYPE_THIRD)
        }

        layout_fourth.setOnSingleClickListener {
            if (currentItem == TabItem.TAB_TYPE_FOURTH) {
                return@setOnSingleClickListener
            }
            clickItem(TabItem.TAB_TYPE_FOURTH)
        }

        layout_fifth.setOnSingleClickListener {
            if (currentItem == TabItem.TAB_TYPE_FIFTH) {
                return@setOnSingleClickListener
            }
            clickItem(TabItem.TAB_TYPE_FIFTH)
        }
    }

    public fun clickItem(item: TabItem = TabItem.TAB_TYPE_FIRST) {
        clickItem = item
        onClickTab?.invoke(clickItem)
        tabItems[currentItem.value].isSelected = false
        tabItems[item.value].isSelected = true
        currentItem = clickItem
    }
}

enum class TabItem(val value: Int) {
    TAB_TYPE_FIRST(0),
    TAB_TYPE_SECOND(1),
    TAB_TYPE_THIRD(2),
    TAB_TYPE_FOURTH(3),
    TAB_TYPE_FIFTH(4)
}

fun Int.toTab(): TabItem {
    return when (this) {
        TabItem.TAB_TYPE_FIRST.value -> {
            TabItem.TAB_TYPE_FIRST
        }
        TabItem.TAB_TYPE_SECOND.value -> {
            TabItem.TAB_TYPE_SECOND
        }
        TabItem.TAB_TYPE_THIRD.value -> {
            TabItem.TAB_TYPE_THIRD
        }
        TabItem.TAB_TYPE_FOURTH.value -> {
            TabItem.TAB_TYPE_FOURTH
        }
        TabItem.TAB_TYPE_FIFTH.value -> {
            TabItem.TAB_TYPE_FIFTH
        }
        else -> {
            TabItem.TAB_TYPE_FIRST
        }
    }
}