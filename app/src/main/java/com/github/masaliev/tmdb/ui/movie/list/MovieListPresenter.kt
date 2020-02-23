package com.github.masaliev.tmdb.ui.movie.list

import com.github.masaliev.tmdb.data.remote.repository.MoviesRepository
import com.github.masaliev.tmdb.ui.base.BasePresenter
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MovieListPresenter(
    schedulerProvider: SchedulerProvider,
    private val moviesRepository: MoviesRepository
) :
    BasePresenter<MovieListContract.View>(schedulerProvider), MovieListContract.Presenter {

    private var searchQuery: String = ""
    private val searchQueryPublishSubject: PublishSubject<String> = PublishSubject.create()

    private var nextPage: Int = 1
    private var hasNext = true
    private var isLoading = false

    override fun viewIsReady() {
        compositeDisposable.add(
            searchQueryPublishSubject.debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(schedulerProvider.ui())
                .subscribe ({ query ->
                    searchQuery = query
                    nextPage = 1
                    hasNext = true
                    isLoading = false
                    view.clearMovies()
                    fetchNextPage()
                }, {throwable ->
                    throwable.printStackTrace()
                })
        )
        if (nextPage == 1) {
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
                moviesRepository.searchMovies(nextPage, searchQuery)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ paginationResult ->
                        view.hideLoading()
                        if (nextPage == 1) {
                            view.populateMovies(paginationResult.results)
                        } else {
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

    override fun publishSearchQuery(query: String) {
        searchQueryPublishSubject.onNext(query)
    }

    override fun getSavedQuery(): String = searchQuery

}