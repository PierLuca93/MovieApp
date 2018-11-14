package it.android.luca.movieapp.home.presenter

import io.reactivex.Observable
import it.android.luca.movieapp.model.MoviesList
import it.android.luca.movieapp.network.MovieService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DefaultHomePresenterTest {

    @Mock
    lateinit var service: MovieService

    @Mock
    lateinit var view: DefaultHomePresenter.View

    private lateinit var presenter: DefaultHomePresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DefaultHomePresenter(service, view)
    }

    @Test
    fun test_onStartUp_fetchFirstPageMovies() {
        //Given
        val movies = MoviesList(1, 123, 123, listOf())

        //When
        `when`(service.getTopRated(1)).thenReturn(Observable.just(movies))

        presenter.fetchMovies()

        //Then
        verify(view).showMovies(movies.results)
    }

    @Test
    fun test_onScrollDown_fetchOtherPageMovies() {
        //Given
        val movies = MoviesList(1, 123, 123, listOf())

        //When
        `when`(service.getTopRated(2)).thenReturn(Observable.just(movies))

        presenter.loadNextPage(2)

        //Then
        verify(view).showMovies(movies.results)
    }

    @Test
    fun test_onStartUp_fetchFirstPageWithError_showError() {
        //Given
        val error = "Endpoint not reachable"

        //When
        `when`(service.getTopRated(1)).thenReturn(Observable.error(Exception(error)))

        presenter.fetchMovies()

        //Then
        verify(view).showError(error)
    }
}