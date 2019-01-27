package it.android.luca.movieapp.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.android.luca.movieapp.model.MoviesList
import it.android.luca.movieapp.repository.Movie

open class MovieService(private val api: MovieApi) {

    open fun getTopRated(page: Int): Observable<MoviesList?> =
        api.topRated(page.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    open fun getMovie(id: String): Observable<Movie> =
        api.movie(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}