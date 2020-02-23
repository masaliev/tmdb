package com.github.masaliev.tmdb.data.local.db

import com.github.masaliev.tmdb.data.local.db.entity.Like
import io.reactivex.Completable

class AppDatabaseHelper(private val appDatabase: AppDatabase) : DatabaseHelper {
    override fun like(movieId: Int): Completable = appDatabase.getLikeDao().insert(Like(0, movieId))

    override fun dislike(movieId: Int): Completable =
        appDatabase.getLikeDao().deleteByMovieId(movieId)

    override fun isLiked(movieId: Int): Boolean = appDatabase.getLikeDao().isLiked(movieId)

}