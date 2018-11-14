package it.android.luca.movieapp.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("vote_count")
    @Expose
    val vote_count: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("video")
    @Expose
    val video: Boolean,
    @SerializedName("vote_average")
    @Expose
    val vote_average: Float,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Float,
    @SerializedName("poster_path")
    @Expose
    val poster_path: String,
    @SerializedName("original_language")
    @Expose
    val original_language: String,
    @SerializedName("original_title")
    @Expose
    val original_title: String,
    @SerializedName("genre_ids")
    @Expose
    val genre_ids: Array<Int>,
    @SerializedName("backdrop_path")
    @Expose
    val backdrop_path: String,
    @SerializedName("adult")
    @Expose
    val adult: Boolean,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("release_date")
    @Expose
    val release_date: String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false

        return true
    }
}