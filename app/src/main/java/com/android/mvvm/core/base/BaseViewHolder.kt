package com.android.mvvm.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding>(protected val binding: VB) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(
        position: Int,
        item: Any?,
        onItemClick: ((position: Int, action: Any) -> Unit)?
    )
}