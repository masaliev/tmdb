package com.github.masaliev.tmdb.ui.movie.details.di

import com.github.masaliev.tmdb.data.local.db.DatabaseHelper
import com.github.masaliev.tmdb.data.remote.repository.MoviesRepository
import com.github.masaliev.tmdb.ui.movie.details.MovieDetailsContract
import com.github.masaliev.tmdb.ui.movie.details.MovieDetailsPresenter
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {
    @Provides
    @MovieDetailsScope
    fun provideMovieDetailsPresenter(
        schedulerProvider: SchedulerProvider,
        moviesRepository: MoviesRepository,
        databaseHelper: DatabaseHelper
    ): MovieDetailsContract.Presenter =
        MovieDetailsPresenter(schedulerProvider, moviesRepository, databaseHelper)
}