package com.github.masaliev.tmdb

import android.app.Application
import android.content.Context
import com.github.masaliev.tmdb.di.AppComponent
import com.github.masaliev.tmdb.di.modules.AppModule
import com.github.masaliev.tmdb.ui.movie.list.di.MovieListComponent


class App : Application() {

    lateinit var component: AppComponent
        private set

    private var mMovieListComponent: MovieListComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = prepareAppComponent()
    }


    private fun prepareAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getMovieListComponent(): MovieListComponent {
        return mMovieListComponent ?: component.plusMovieListComponent().also {
            mMovieListComponent = it
        }
    }

    fun clearMovieListComponent() {
        mMovieListComponent = null
    }

    companion object {
        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}