package com.android.mvvm.ui.test.adapter

import com.android.mvvm.R
import com.android.mvvm.core.base.BaseLoadAdapter
import com.android.mvvm.data.RepoBean
import com.android.mvvm.ui.test.adapter.viewholder.ReposViewHolder

class ReposAdapter : BaseLoadAdapter() {

    companion object {
        private const val TAG = "ReposAdapter"
    }

    init {
        addItemType(TYPE_ITEM, R.layout.item_repo, ReposViewHolder::class.java)
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