package com.android.mvvm.ui.test.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.R
import com.android.mvvm.core.extension.inflate
import com.android.mvvm.data.RepoBean
import com.android.mvvm.ui.test.adapter.viewholder.BaseViewHolder
import com.android.mvvm.ui.test.adapter.viewholder.ReposViewHolder

class ReposAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val TAG = "ReposAdapter"
    }

    private val list = mutableListOf<RepoBean>()

    /**
     * 初始化适配器的数据集
     *
     * @param repos 包含用于填充要由RecyclerView使用的视图的数据
     */
    fun setData(repos: List<RepoBean>) {
        list.clear()
        list.addAll(repos)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    // 创建新视图（由布局管理器调用）
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder {
        return ReposViewHolder(viewGroup.inflate(R.layout.item_repo))
    }

    // 替换视图的内容（由布局管理器调用）
    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        // 从此位置的数据集中获取元素，然后用该元素替换视图的内容
        viewHolder.bindData(position, list[position])
    }
}