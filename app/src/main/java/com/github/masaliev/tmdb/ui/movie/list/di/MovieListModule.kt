package com.github.masaliev.tmdb.ui.movie.list.di

import com.github.masaliev.tmdb.ui.movie.list.MovieListPresenter
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {
    @Provides
    @MovieListScope
    fun provideMovieListPresenter(schedulerProvider: SchedulerProvider) =
        MovieListPresenter(schedulerProvider)
}