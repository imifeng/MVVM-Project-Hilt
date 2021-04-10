package com.android.mvvm.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindData(
        position: Int,
        item: Any?,
        onItemClick: ((position: Int, action: Any) -> Unit)?
    )
}