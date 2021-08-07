package com.android.mvvm.ui.test.adapter.viewholder

import android.view.ViewGroup
import com.android.mvvm.core.base.BaseViewHolder
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.data.RepoBean
import com.android.mvvm.databinding.ItemRepoBinding
import com.android.mvvm.util.Logger
import com.android.mvvm.util.loadRound

class RepoPageViewHolder(parent: ViewGroup) :
    BaseViewHolder<ItemRepoBinding>(parent.viewBinding(ItemRepoBinding::inflate)) {

    companion object {
        private const val TAG = "ReposViewHolder"
    }

    override fun bindData(
        position: Int,
        item: Any?,
        onItemClick: ((position: Int, action: Any) -> Unit)?
    ) {
        with(binding) {
            if (item != null && item is RepoBean) {
                ivAvatar.loadRound(item.owner?.avatar_url)
                tvName.text = item.name
                tvDescription.text = item.description ?: ""

                // 为ViewHolder的视图定义点击监听器
                root.setOnClickListener {
                    Logger.d(TAG, "Element $adapterPosition clicked.")
                    onItemClick?.invoke(position, item)
                }
            }
        }
    }
}