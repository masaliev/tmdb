package com.github.masaliev.tmdb.ui.movie.details

import androidx.databinding.ObservableField
import com.github.masaliev.tmdb.data.local.db.DatabaseHelper
import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.data.remote.repository.MoviesRepository
import com.github.masaliev.tmdb.ui.base.BasePresenter
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider

class MovieDetailsPresenter(
    schedulerProvider: SchedulerProvider,
    private val moviesRepository: MoviesRepository,
    private val databaseHelper: DatabaseHelper
) : BasePresenter<MovieDetailsContract.View>(schedulerProvider), MovieDetailsContract.Presenter {

    private val movieObservableField = ObservableField<Movie>()
    private var mMovieId: Int = 0

    override fun setMovieId(movieId: Int) {
        mMovieId = movieId
    }

    override fun viewIsReady() {
        movieObservableField.get()?.let { movie ->
            if (movie.id == mMovieId) {
                onFetchDetailsSuccess()
            } else {
                null
            }
        } ?: fetchMovieDetails()
    }

    private fun fetchMovieDetails() {
        view.showLoading()
        compositeDisposable.add(
            moviesRepository.getMovieDetails(mMovieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ movie ->
                    view.hideLoading()
                    movieObservableField.set(movie)
                    onFetchDetailsSuccess()
                }, { throwable ->
                    view.hideLoading()
                    //@TODO handle exception
                })
        )
    }

    private fun onFetchDetailsSuccess() {
        movieObservableField.get()?.let { movie ->
            view.setTitle(movie.title)
            view.isLiked(movie.isLiked)
        }
    }

    override fun like() {
        compositeDisposable.add(
            databaseHelper.like(mMovieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view.isLiked(true)
                }, { throwable ->
                    //@TODO handle exception
                })
        )
    }

    override fun dislike() {
        compositeDisposable.add(
            databaseHelper.dislike(mMovieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view.isLiked(false)
                }, { throwable ->
                    //@TODO handle exception
                })
        )
    }

    override fun getMovie(): ObservableField<Movie> = movieObservableField

}