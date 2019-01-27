package it.android.luca.movieapp.home.presenter

interface HomePresenter {
    fun dispose()
    fun fetchMovies()
    fun loadNextPage(page: Int)
}