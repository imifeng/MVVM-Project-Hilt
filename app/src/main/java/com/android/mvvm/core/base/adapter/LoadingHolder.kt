package com.android.mvvm.core.base.adapter

import android.view.ViewGroup
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.ItemLoadingProgressBinding

class LoadingHolder(parent: ViewGroup) :
    BaseViewHolder<ItemLoadingProgressBinding, Any>(parent.viewBinding(ItemLoadingProgressBinding::inflate)) {
    override fun bindData(
        position: Int,
        item: Any?,
        onItemClick: ((position: Int, action: Any) -> Unit)?,
        vararg params: Any?
    ) {
    }
}
