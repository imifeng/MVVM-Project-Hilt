package com.android.mvvm.ui.test.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    abstract fun bindData(position: Int, item: Any?)
}