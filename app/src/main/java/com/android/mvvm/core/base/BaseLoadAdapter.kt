package com.android.mvvm.core.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.R
import com.android.mvvm.core.extension.inflate
import com.android.mvvm.util.Logger
import java.lang.reflect.Constructor
import java.lang.reflect.Modifier

open class BaseLoadAdapter : RecyclerView.Adapter<BaseViewHolder>() {

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
        @LayoutRes layoutResId: Int,
        holderClass: Class<*>
    ) {
        holderLayouts[type] = HolderType(layoutResId, holderClass)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val holderType = holderLayouts[viewType]!!
        val view = parent.inflate(holderType.layoutId)
        Logger.d(TAG, "holderType: $holderType view: $view")
        return createConstructorByClass(holderType.holderClass, view)
    }

    override fun getItemCount(): Int {
        return recyclerData.size + getLoadMoreViewCount()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
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
        notifyItemRangeInserted(recyclerData.size - data.size , data.size)
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
        addItemType(TYPE_ITEM_LOAD_MORE, R.layout.item_loading_progress, LoadingHolder::class.java)
    }

    private fun loadMoreComplete() {
        loading = false
        if (getLoadMoreViewCount() > 0) {
            notifyItemChanged(recyclerData.size)
        }
    }

    private fun createConstructorByClass(
        z: Class<*>,
        view: View
    ): BaseViewHolder {
        val constructor: Constructor<*>
        // inner and unstatic class
        return if (z.isMemberClass && !Modifier.isStatic(z.modifiers)) {
            constructor = z.getDeclaredConstructor(javaClass, View::class.java)
            constructor.isAccessible = true
            constructor.newInstance(this, view) as BaseViewHolder
        } else {
            constructor = z.getDeclaredConstructor(View::class.java)
            constructor.isAccessible = true
            constructor.newInstance(view) as BaseViewHolder
        }
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
}

class LoadingHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun bindData(
        position: Int,
        item: Any?,
        onItemClick: ((position: Int, action: Any) -> Unit)?
    ) {

    }
}

data class HolderType(
    @LayoutRes val layoutId: Int,
    val holderClass: Class<*>
)