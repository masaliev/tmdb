package com.github.masaliev.tmdb.ui.movie.details.di

import com.github.masaliev.tmdb.ui.movie.details.MovieDetailsActivity
import dagger.Subcomponent

@MovieDetailsScope
@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsComponent {
    fun inject(activity: MovieDetailsActivity)
}