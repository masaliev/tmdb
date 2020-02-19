package com.github.masaliev.tmdb.di

import com.github.masaliev.tmdb.di.modules.AppModule
import com.github.masaliev.tmdb.ui.movie.list.di.MovieListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun plusMovieListComponent(): MovieListComponent
}