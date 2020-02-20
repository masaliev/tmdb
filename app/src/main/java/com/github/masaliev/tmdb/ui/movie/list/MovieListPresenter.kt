package com.github.masaliev.tmdb.ui.movie.list

import com.github.masaliev.tmdb.data.remote.repository.MoviesRepository
import com.github.masaliev.tmdb.ui.base.BasePresenter
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider

class MovieListPresenter(
    schedulerProvider: SchedulerProvider,
    private val moviesRepository: MoviesRepository
) :
    BasePresenter<MovieListContract.View>(schedulerProvider), MovieListContract.Presenter {

    private var nextPage: Int = 1
    private var hasNext = true
    private var isLoading = false;

    override fun viewIsReady() {
        fetchNextPage()
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
                        if (nextPage == 1){
                            view.populateMovies(paginationResult.results)
                        }else{
                            view.addMovies(paginationResult.results)
                        }
                        nextPage++
                        hasNext = nextPage <= paginationResult.totalPages
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