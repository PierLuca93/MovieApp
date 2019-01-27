package it.android.luca.movieapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesList(
    val page: Int,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int,
    val results: List<Movie>)