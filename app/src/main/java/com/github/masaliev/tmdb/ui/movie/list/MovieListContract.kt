package com.github.masaliev.tmdb.ui.movie.list

import com.github.masaliev.tmdb.ui.base.MvpPresenter
import com.github.masaliev.tmdb.ui.base.MvpView

interface MovieListContract {
    interface View : MvpView {

    }

    interface Presenter : MvpPresenter<View> {

    }
}