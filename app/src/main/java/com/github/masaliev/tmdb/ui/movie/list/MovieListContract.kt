package com.github.masaliev.tmdb.ui.movie.list

import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.ui.base.MvpPresenter
import com.github.masaliev.tmdb.ui.base.MvpView

interface MovieListContract {
    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun clearMovies()
        fun populateMovies(movies: List<Movie>)
        fun addMovies(movies: List<Movie>)
    }

    interface Presenter : MvpPresenter<View> {
        fun fetchNextPage()
        fun publishSearchQuery(query: String)
        fun getSavedQuery() : String
    }
}