package com.android.mvvm.ui.test.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.core.base.adapter.RecyclerViewDiffer
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.ItemThirdBinding
import com.android.mvvm.ui.test.adapter.viewholder.ThirdListViewHolder
import kotlin.properties.Delegates

class ThirdListAdapter : RecyclerView.Adapter<ThirdListViewHolder>(), RecyclerViewDiffer {

    // 使用 Diff Util 来刷新列表数据
    var datas: List<String> by Delegates.observable(emptyList()) {
            _, oldValue, newValue ->
        notifyDataDispatchUpdate(oldValue, newValue) { old, new -> old == new }
    }

    var onItemClick: ((position: Int, action: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdListViewHolder {
        return ThirdListViewHolder(parent.viewBinding(ItemThirdBinding::inflate))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ThirdListViewHolder, position: Int) {
        holder.bindData(
            position,
            datas[position],
            onItemClick
        )
    }
}