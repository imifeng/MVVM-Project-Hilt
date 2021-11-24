package com.android.mvvm.core.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseLoadAdapter<VB : ViewBinding, T> : RecyclerView.Adapter<BaseViewHolder<VB, T>>() {

    companion object {
        const val TAG = "LoadAdapter"
        const val TYPE_ITEM = 1
        const val TYPE_ITEM_LOAD_MORE = 0x00000222
    }

    var onItemClick: ((position: Int, action: Any) -> Unit)? = null
    var onLoadMore: (() -> Unit)? = null

    private var recyclerView: RecyclerView? = null
    private val holderLayouts = hashMapOf<Int, HolderType>()
    private val recyclerData: MutableList<T> = mutableListOf()

    private var loading = false
    private var loadMoreEnable = false
    private val startLoadMoreIndex = 5

    init {
        addLoadMoreItem()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    protected fun addItemType(
        type: Int,
        holderClass: Class<*>
    ) {
        holderLayouts[type] = HolderType(holderClass)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == recyclerData.size) {
            if (getLoadMoreViewCount() == 1) {
                TYPE_ITEM_LOAD_MORE
            } else {
                onItemViewType(position)
            }
        } else {
            onItemViewType(position)
        }
    }

    protected open fun onItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB, T> {
        val holderType = holderLayouts[viewType]!!
        return createConstructorByClass(holderType.holderClass, parent)
    }

    override fun getItemCount(): Int {
        return recyclerData.size + getLoadMoreViewCount()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB, T>, position: Int) {
        holder.bindData(position, getItemData(position), onItemClick, position, "test")
        autoLoadMore(position)
    }

    private fun getItemData(position: Int): T? {
        return if (position == recyclerData.size && getLoadMoreViewCount() == 1) {
            null
        } else {
            recyclerData[position]
        }
    }

    fun setRecyclerData(data: List<T>) {
        recyclerData.clear()
        recyclerData.addAll(data)
        notifyDataSetChanged()
    }

    fun addRecyclerData(data: List<T>) {
        recyclerData.addAll(data)
        notifyItemRangeInserted(recyclerData.size - data.size, data.size)
        loadMoreComplete()
    }

    private fun autoLoadMore(position: Int) {
        if (itemCount < startLoadMoreIndex) {
            return
        }
        if (getLoadMoreViewCount() == 0) {
            return
        }
        // 提前加载“startLoadMoreIndex”位置
        if (position < itemCount - getLoadMoreViewCount() - startLoadMoreIndex) {
            return
        }
        if (!loading) {
            loading = true
            recyclerView?.apply {
                post { onLoadMore?.invoke() }
            } ?: run {
                onLoadMore?.invoke()
            }
        }
    }

    fun setLoadMoreEnable(isLoadMore: Boolean) {
        loadMoreEnable = isLoadMore
    }

    private fun addLoadMoreItem() {
        addItemType(TYPE_ITEM_LOAD_MORE, LoadingHolder::class.java)
    }

    private fun loadMoreComplete() {
        loading = false
        if (getLoadMoreViewCount() > 0) {
            notifyItemChanged(recyclerData.size)
        }
    }

    private fun <C> createConstructorByClass(
        clz: Class<C>,
        parent: ViewGroup
    ): BaseViewHolder<VB, T> {
        val create = clz.getDeclaredConstructor(ViewGroup::class.java).apply {
            isAccessible = true
        }
        return create.newInstance(parent) as BaseViewHolder<VB, T>
    }

    private fun getLoadMoreViewCount(): Int {
        if (onLoadMore == null || !loadMoreEnable) {
            return 0
        }
        if (holderLayouts[TYPE_ITEM_LOAD_MORE] == null) {
            return 0
        }
        return if (recyclerData.isEmpty()) {
            0
        } else 1
    }

    data class HolderType(
        val holderClass: Class<*>
    )
}