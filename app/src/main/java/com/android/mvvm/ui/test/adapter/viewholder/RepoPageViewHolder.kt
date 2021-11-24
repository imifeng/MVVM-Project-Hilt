package com.android.mvvm.ui.test.adapter.viewholder

import android.view.ViewGroup
import com.android.mvvm.core.base.adapter.BaseViewHolder
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.data.RepoBean
import com.android.mvvm.databinding.ItemRepoBinding
import com.android.mvvm.util.Logger
import com.android.mvvm.util.loadRound

class RepoPageViewHolder(parent: ViewGroup) :
    BaseViewHolder<ItemRepoBinding, RepoBean>(parent.viewBinding(ItemRepoBinding::inflate)) {

    companion object {
        private const val TAG = "ReposViewHolder"
    }

    override fun bindData(
        position: Int,
        item: RepoBean?,
        onItemClick: ((position: Int, action: Any) -> Unit)?,
        vararg params: Any?
    ) {
        with(itemView) {
            if (item != null) {
                binding.ivAvatar.loadRound(item.owner?.avatar_url)
                binding.tvName.text = item.name
                binding.tvDescription.text = item.description ?: ""

                // 示例获取更多参数
                params.let {
                    val firstParam = params[0] as Int
                    val secondParam = params[1] as String
                    Logger.d(TAG, "params: firstParam = $firstParam, secondParam = $secondParam")
                }

                // 为ViewHolder的视图定义点击监听器
                setOnClickListener {
                    Logger.d(TAG, "Element $adapterPosition clicked.")
                    onItemClick?.invoke(position, item)
                }


            }
        }
    }
}