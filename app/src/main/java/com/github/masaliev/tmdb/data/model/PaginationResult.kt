package com.github.masaliev.tmdb.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaginationResult<T>(
    @Expose val page: Int,
    @Expose @SerializedName("total_results") val totalResults: Int,
    @Expose @SerializedName("total_pages") val totalPages: Int,
    @Expose val results: List<T>
) : Serializable