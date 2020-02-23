package com.github.masaliev.tmdb.ui.movie.list.di

import com.github.masaliev.tmdb.data.remote.repository.MoviesRepository
import com.github.masaliev.tmdb.ui.movie.list.MovieAdapter
import com.github.masaliev.tmdb.ui.movie.list.MovieListContract
import com.github.masaliev.tmdb.ui.movie.list.MovieListPresenter
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {
    @Provides
    @MovieListScope
    fun provideMovieListPresenter(
        schedulerProvider: SchedulerProvider,
        moviesRepository: MoviesRepository
    ): MovieListContract.Presenter =
        MovieListPresenter(schedulerProvider, moviesRepository)

    @Provides
    @MovieListScope
    fun provideMovieAdapter() = MovieAdapter()
}