package com.github.masaliev.tmdb.di.modules

import android.app.Application
import android.content.Context
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProvider()
    }
}