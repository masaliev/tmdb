package com.github.masaliev.tmdb.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.masaliev.tmdb.data.local.db.entity.Like
import io.reactivex.Completable

@Dao
abstract class LikeDao {
    @Insert
    abstract fun insert(like: Like): Completable

    @Query("DELETE FROM `Like` WHERE movieId = :movieId")
    abstract fun deleteByMovieId(movieId: Int): Completable

    @Query("SELECT count(*) FROM `Like` WHERE movieId = :movieId")
    abstract fun getCountByMovieId(movieId: Int): Int

    fun isLiked(movieId: Int): Boolean = getCountByMovieId(movieId) > 0
}