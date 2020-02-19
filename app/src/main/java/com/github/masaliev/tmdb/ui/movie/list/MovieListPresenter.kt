package com.github.masaliev.tmdb.ui.movie.list

import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.data.remote.repository.MoviesRepository
import com.github.masaliev.tmdb.ui.base.BasePresenter
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider

class MovieListPresenter(
    schedulerProvider: SchedulerProvider,
    private val moviesRepository: MoviesRepository
) :
    BasePresenter<MovieListContract.View>(schedulerProvider), MovieListContract.Presenter {

    private val movies = ArrayList<Movie>()
    private var nextPage: Int = 1
    private var hasNext = true
    private var isLoading = false;

    override fun viewIsReady() {
        if (movies.size > 0) {
            view.populateMovies(movies)
        } else {
            fetchNextPage()
        }
    }

    override fun fetchNextPage() {
        if (hasNext) {
            if (isLoading) {
                return
            }
            isLoading = true
            view.showLoading()
            compositeDisposable.add(
                moviesRepository.searchMovies(nextPage)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ paginationResult ->
                        view.hideLoading()
                        movies.addAll(paginationResult.results)
                        nextPage++
                        hasNext = nextPage <= paginationResult.totalPages
                        view.addMovies(paginationResult.results)
                        isLoading = false
                    }, { throwable ->
                        view.hideLoading()
                        isLoading = false
                        //@TODO handle throwable
                    })
            )
        } else {
            //@TODO may be need to show message
        }
    }

}