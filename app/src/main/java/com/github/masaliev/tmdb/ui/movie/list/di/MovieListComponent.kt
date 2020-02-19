package com.github.masaliev.tmdb.ui.movie.list.di

import com.github.masaliev.tmdb.ui.movie.list.MovieListActivity
import dagger.Subcomponent

@MovieListScope
@Subcomponent(modules = [MovieListModule::class])
interface MovieListComponent {
    fun inject(activity: MovieListActivity)
}