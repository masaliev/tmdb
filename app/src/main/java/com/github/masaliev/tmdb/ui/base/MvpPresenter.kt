package com.github.masaliev.tmdb.ui.base

interface MvpPresenter<V:MvpView>{
    fun attachView(mvpView: V)
    fun viewIsReady()
    fun detachView()
    fun destroy()
}