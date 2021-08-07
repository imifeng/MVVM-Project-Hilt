package com.android.mvvm.core.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.android.mvvm.core.extension.viewBinding
import com.android.mvvm.databinding.ItemLoadingProgressBinding
import com.android.mvvm.util.Logger

open class BaseLoadAdapter<VB : ViewBinding> : RecyclerView.Adapter<BaseViewHolder<VB>>() {

    companion object {
        const val TAG = "LoadAdapter"
        const val TYPE_ITEM = 1
        const val TYPE_ITEM_LOAD_MORE = 0x00000222
    }

    var onItemClick: ((position: Int, action: Any) -> Unit)? = null
    var onLoadMore: (() -> Unit)? = null

    private var recyclerView: RecyclerView? = null
    private val holderLayouts = hashMapOf<Int, HolderType>()
    protected var recyclerData: ArrayList<Any> = arrayListOf()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val holderType = holderLayouts[viewType]!!
//        val view = parent.inflate(holderType.layoutId)
        return createConstructorByClass(holderType.holderClass, parent)
    }

    override fun getItemCount(): Int {
        return recyclerData.size + getLoadMoreViewCount()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        autoLoadMore(position)
        holder.bindData(position, getItemData(position), onItemClick)
    }

    private fun getItemData(position: Int): Any? {
        return if (position == recyclerData.size && getLoadMoreViewCount() == 1) {
            null
        } else {
            recyclerData[position]
        }
    }

    fun setRecyclerData(data: List<Any>) {
        recyclerData.clear()
        recyclerData.addAll(data)
        notifyDataSetChanged()
    }

    fun addRecyclerData(data: List<Any>) {
        recyclerData.addAll(data)
        Logger.d(
            TAG,
            "notifyItemRangeInserted: start: ${recyclerData.size - data.size}, data.size: ${data.size}"
        )
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
        //Load "startLoadMoreIndex" positions in advance
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
        Logger.d(TAG, "isLoadMore: $isLoadMore")
        loadMoreEnable = isLoadMore
    }

    private fun addLoadMoreItem() {
        Logger.d(TAG, "addLoadMoreItem")
        addItemType(TYPE_ITEM_LOAD_MORE, LoadingHolder::class.java)
    }

    private fun loadMoreComplete() {
        loading = false
        if (getLoadMoreViewCount() > 0) {
            notifyItemChanged(recyclerData.size)
        }
    }

    private fun <T> createConstructorByClass(
        clz: Class<T>,
        parent: ViewGroup
    ): BaseViewHolder<VB> {
        val create = clz.getDeclaredConstructor(ViewGroup::class.java).apply {
            isAccessible = true
        }
        return create.newInstance(parent) as BaseViewHolder<VB>
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

    inner class LoadingHolder(parent: ViewGroup) :
        BaseViewHolder<ItemLoadingProgressBinding>(parent.viewBinding(ItemLoadingProgressBinding::inflate)) {
        override fun bindData(
            position: Int,
            item: Any?,
            onItemClick: ((position: Int, action: Any) -> Unit)?
        ) {
        }
    }

    data class HolderType(
        val holderClass: Class<*>
    )
}