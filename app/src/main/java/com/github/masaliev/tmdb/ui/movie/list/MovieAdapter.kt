package com.github.masaliev.tmdb.ui.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.databinding.ListItemMovieBinding
import com.github.masaliev.tmdb.ui.base.BaseLoadMoreAdapter
import com.github.masaliev.tmdb.ui.base.BaseViewHolder

class MovieAdapter : BaseLoadMoreAdapter<Movie>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun getItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return MovieViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MovieViewHolder internal constructor(private val mBinding: ListItemMovieBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            val item = items[position]
            mBinding.movie = item

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()

            mBinding.root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
        }

    }

    interface OnItemClickListener {
        fun onClick(movie: Movie)
    }
}