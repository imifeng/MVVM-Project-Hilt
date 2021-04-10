package com.android.mvvm.ui.test.adapter.viewholder

import android.view.View
import com.android.mvvm.core.base.BaseViewHolder
import com.android.mvvm.data.RepoBean
import com.android.mvvm.util.Logger
import com.android.mvvm.util.loadRound
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposViewHolder(itemView: View) : BaseViewHolder(itemView) {

    companion object {
        private const val TAG = "ReposViewHolder"
    }

    override fun bindData(
        position: Int,
        item: Any?,
        onItemClick: ((position: Int, action: Any) -> Unit)?
    ) {
        with(itemView) {
            if (item != null && item is RepoBean) {
                iv_avatar.loadRound(item.owner?.avatar_url)
                tv_name.text = item.name
                tv_description.text = item?.description ?: ""

                // 为ViewHolder的视图定义点击监听器
                setOnClickListener {
                    Logger.d(TAG, "Element $adapterPosition clicked.")
                    onItemClick?.invoke(position, item)
                }
            }
        }
    }
}