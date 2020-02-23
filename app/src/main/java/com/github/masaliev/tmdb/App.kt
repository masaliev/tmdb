package com.github.masaliev.tmdb

import android.app.Application
import android.content.Context
import com.github.masaliev.tmdb.di.AppComponent
import com.github.masaliev.tmdb.di.DaggerAppComponent
import com.github.masaliev.tmdb.di.modules.AppModule
import com.github.masaliev.tmdb.di.modules.NetworkModule
import com.github.masaliev.tmdb.ui.movie.details.di.MovieDetailsComponent
import com.github.masaliev.tmdb.ui.movie.list.di.MovieListComponent


class App : Application() {

    lateinit var component: AppComponent
        private set

    private var mMovieListComponent: MovieListComponent? = null

    private var mMovieDetailsComponent: MovieDetailsComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = prepareAppComponent()
    }


    private fun prepareAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(
                NetworkModule(
                    getString(R.string.api_base_url),
                    getString(R.string.api_key)
                )
            )
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

    fun getMovieDetailsComponent(): MovieDetailsComponent {
        return mMovieDetailsComponent ?: component.plusMovieDetailsComponent().also {
            mMovieDetailsComponent = it
        }
    }

    fun clearMovieDetailsComponent() {
        mMovieDetailsComponent = null
    }

    companion object {
        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}