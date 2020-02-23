package com.github.masaliev.tmdb.data.local.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index("id"), Index("movieId")]
)
data class Like(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val movieId: Int
)