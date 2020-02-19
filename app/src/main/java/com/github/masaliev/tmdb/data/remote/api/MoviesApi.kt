package com.github.masaliev.tmdb.data.remote.api

import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.data.model.PaginationResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<PaginationResult<Movie>>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("page") page: Int): Single<PaginationResult<Movie>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Single<Movie>

}