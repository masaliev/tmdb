package com.github.masaliev.tmdb.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.github.masaliev.tmdb.R

abstract class BaseLoadMoreAdapter<T> : BaseAdapter<T>() {

    companion object {
        const val ITEM_TYPE_LOADING = 1
        const val ITEM_TYPE_ITEM = 2
    }

    private var isLoading = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == ITEM_TYPE_LOADING) {
            return LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        getLoadingLayoutId(),
                        parent,
                        false
                    )
            )
        } else {
            return getItemViewHolder(parent, viewType)
        }
    }

    @LayoutRes
    open fun getLoadingLayoutId(): Int {
        return R.layout.list_item_loading
    }

    abstract fun getItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if (isLoading) itemCount + 1 else itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= items.size) ITEM_TYPE_LOADING else ITEM_TYPE_ITEM
    }

    open fun setLoading(isLoading: Boolean = true) {
        if (this.isLoading != isLoading) {
            this.isLoading = isLoading
            if (isLoading) {
                notifyItemInserted(items.size)
            } else {
                notifyItemRemoved(items.size)
            }
        }
    }

    inner class LoadingViewHolder internal constructor(mView: View) :
        BaseViewHolder(mView) {
        override fun onBind(position: Int) {
            //Do nothing
        }

    }

}