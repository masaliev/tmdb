package com.github.masaliev.tmdb.data.local.db

import io.reactivex.Completable

interface DatabaseHelper {
    fun like(movieId: Int): Completable

    fun dislike(movieId: Int): Completable

    fun isLiked(movieId: Int): Boolean
}