package com.github.masaliev.tmdb.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    val items: MutableList<T> = ArrayList()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    open fun setItems(items: List<T>?) {
        this.items.apply {
            clear()
            if (items != null) {
                addAll(items)
            }
        }
        notifyDataSetChanged()
    }

    open fun addItems(items: List<T>?) {
        if (items != null) {
            val start = this.items.size
            val itemCount = items.size
            this.items.addAll(items)
            notifyItemRangeInserted(start, itemCount)
        }
    }

    open fun replaceItem(item: T) {
        val position = this.items.indexOf(item)
        if (position >= 0) {
            this.items.set(position, item)
            notifyItemChanged(position)
        }
    }

    open fun removeItem(item: T) {
        val position = this.items.indexOf(item)
        if (position >= 0) {
            this.items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}