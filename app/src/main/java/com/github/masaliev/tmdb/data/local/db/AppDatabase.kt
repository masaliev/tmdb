package com.github.masaliev.tmdb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.masaliev.tmdb.data.local.db.dao.LikeDao
import com.github.masaliev.tmdb.data.local.db.entity.Like

@Database(entities = [Like::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLikeDao(): LikeDao
}