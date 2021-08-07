package com.android.mvvm.ui.test.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.databinding.ItemThirdBinding

class ThirdListViewHolder(val binding: ItemThirdBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val TAG = "ThirdListViewHolder"
    }

    fun bindData(
        position: Int,
        item: String?,
        onItemClick: ((position: Int, action: String) -> Unit)?
    ) {
        with(binding) {
            if (!item.isNullOrEmpty()) {
                tvNote.text = item

                // 为ViewHolder的视图定义点击监听器
                root.setOnClickListener {
                    onItemClick?.invoke(position, item)
                }
            }
        }
    }
}