package it.android.luca.movieapp.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("video")
    @Expose
    val video: Boolean,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Float,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("popularity")
    @Expose
    val popularity: Float,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String,
    @SerializedName("original_title")
    @Expose
    val originalTitle: String,
    @SerializedName("genre_ids")
    @Expose
    val genreIds: Array<Int>,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String,
    @SerializedName("adult")
    @Expose
    val adult: Boolean,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false

        return true
    }
}