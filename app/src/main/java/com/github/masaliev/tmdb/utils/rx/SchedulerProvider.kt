package com.github.masaliev.tmdb.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider {
    fun ui(): Scheduler = AndroidSchedulers.mainThread()

    fun io(): Scheduler = Schedulers.io()
}