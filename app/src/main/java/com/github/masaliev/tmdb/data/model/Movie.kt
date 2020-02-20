package com.github.masaliev.tmdb.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Movie(
    @Expose val id: Int,
    @Expose val popularity: Float,
    @Expose @SerializedName("vote_count") val voteCount: Int,
    @Expose @SerializedName("vote_average") val voteAverage: Float,
    @Expose val title: String,
    @Expose @SerializedName("release_date") val releaseDate: Date,
    @Expose @SerializedName("overview") val overview: String,
    @Expose @SerializedName("poster_path") val posterPath: String?,
    @Expose @SerializedName("backdrop_path") val backdropPath: String?,
    var isLiked: Boolean = false
) : Serializable