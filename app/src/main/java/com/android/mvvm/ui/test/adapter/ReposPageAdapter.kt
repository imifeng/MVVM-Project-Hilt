package com.android.mvvm.ui.test.adapter

import com.android.mvvm.core.base.BaseLoadAdapter
import com.android.mvvm.data.RepoBean
import com.android.mvvm.databinding.ItemRepoBinding
import com.android.mvvm.ui.test.adapter.viewholder.RepoPageViewHolder

class ReposPageAdapter : BaseLoadAdapter<ItemRepoBinding>() {

    companion object {
        private const val TAG = "ReposAdapter"
    }

    init {
        addItemType(TYPE_ITEM, RepoPageViewHolder::class.java)
    }

    override fun onItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    fun setData(data: List<RepoBean>) {
        setRecyclerData(data)
    }

    fun addData(newData: List<RepoBean>) {
        addRecyclerData(newData)
    }
}