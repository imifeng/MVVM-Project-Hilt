package com.android.mvvm.core.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, T>(protected val binding: VB) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(
        position: Int,
        item: T?,
        onItemClick: ((position: Int, action: Any) -> Unit)?,
        vararg params: Any?
    )
}