package com.github.masaliev.tmdb.data.local.db

import io.reactivex.Completable

/**
 * @TODO Add new class to save to database
 */
class MockDatabaseHelper : DatabaseHelper {
    override fun like(movieId: Int): Completable = Completable.complete()

    override fun dislike(movieId: Int): Completable = Completable.complete()

    override fun isLiked(movieId: Int): Boolean = Math.random() > 0.5

}