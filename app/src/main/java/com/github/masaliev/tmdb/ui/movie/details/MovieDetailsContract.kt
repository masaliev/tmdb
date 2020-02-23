package com.github.masaliev.tmdb.ui.movie.details

import androidx.databinding.ObservableField
import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.ui.base.MvpPresenter
import com.github.masaliev.tmdb.ui.base.MvpView

interface MovieDetailsContract {
    interface View : MvpView {
        fun setTitle(title: String)
        fun showLoading()
        fun hideLoading()
        fun isLiked(isLiked: Boolean)
    }

    interface Presenter : MvpPresenter<View> {
        fun setMovieId(movieId: Int)
        fun like()
        fun dislike()
        fun getMovie(): ObservableField<Movie>
    }
}