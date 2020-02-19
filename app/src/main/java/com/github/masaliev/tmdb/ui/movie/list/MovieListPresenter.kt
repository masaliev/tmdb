package com.github.masaliev.tmdb.ui.movie.list

import com.github.masaliev.tmdb.ui.base.BasePresenter
import com.github.masaliev.tmdb.ui.base.MvpView
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider

class MovieListPresenter(schedulerProvider: SchedulerProvider) : BasePresenter<MvpView>(schedulerProvider){
    override fun viewIsReady() {

    }
}