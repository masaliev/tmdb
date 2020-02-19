package com.github.masaliev.tmdb.ui.base

import com.github.masaliev.tmdb.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MvpView>(val schedulerProvider: SchedulerProvider) :
    MvpPresenter<V> {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var _view: V? = null
    val view: V
        get() = _view!!

    override fun attachView(mvpView: V) {
        _view = mvpView
    }

    override fun detachView() {
        _view = null
    }

    override fun destroy() {
        compositeDisposable.dispose()
    }
}