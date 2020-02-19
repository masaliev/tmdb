package com.github.masaliev.tmdb.di.modules

import com.github.masaliev.tmdb.data.local.db.DatabaseHelper
import com.github.masaliev.tmdb.data.remote.api.MoviesApi
import com.github.masaliev.tmdb.data.remote.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    fun provideMoviesRepository(
        moviesApi: MoviesApi,
        databaseHelper: DatabaseHelper
    ): MoviesRepository = MoviesRepository(moviesApi, databaseHelper)

}