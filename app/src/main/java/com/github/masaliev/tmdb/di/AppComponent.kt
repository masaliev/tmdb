package com.github.masaliev.tmdb.di

import com.github.masaliev.tmdb.di.modules.ApiModule
import com.github.masaliev.tmdb.di.modules.AppModule
import com.github.masaliev.tmdb.di.modules.NetworkModule
import com.github.masaliev.tmdb.ui.movie.list.di.MovieListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ApiModule::class])
interface AppComponent {
    fun plusMovieListComponent(): MovieListComponent
}