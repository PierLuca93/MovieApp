package it.android.luca.movieapp.network

import io.reactivex.Observable
import it.android.luca.movieapp.model.MoviesList
import it.android.luca.movieapp.repository.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {
    @GET("top_rated")
    fun topRated(@Query("page") page: String): Observable<MoviesList?>

    @GET("{id}")
    fun movie(@Path(value = "id", encoded = true) id: String): Observable<Movie>

    companion object {
        val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}