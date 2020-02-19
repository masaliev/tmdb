package com.github.masaliev.tmdb.data.remote.repository

import com.github.masaliev.tmdb.data.local.db.DatabaseHelper
import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.data.model.PaginationResult
import com.github.masaliev.tmdb.data.remote.api.MoviesApi
import io.reactivex.Single

class MoviesRepository(private val api: MoviesApi, private val databaseHelper: DatabaseHelper) {

    fun searchMovies(page: Int = 1, query: String? = null): Single<PaginationResult<Movie>> {
        val single: Single<PaginationResult<Movie>> = query?.let {
            if (it.isEmpty()) {
                null
            } else {
                api.searchMovies(query, page)
            }
        } ?: api.getPopularMovies(page)
        return single
            .map { paginationResult ->
                paginationResult.results.forEach { movie ->
                    movie.isLiked = databaseHelper.isLiked(movie.id)
                }
                paginationResult
            }
    }

    fun getMovieDetails(movieId: Int): Single<Movie> =
        api.getMovieDetails(movieId)
            .map { movie ->
                movie.isLiked = databaseHelper.isLiked(movie.id)
                movie
            }
}