package it.android.luca.movieapp.detail.presenter

import io.reactivex.Observable
import it.android.luca.movieapp.network.MovieService
import it.android.luca.movieapp.repository.Movie
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DefaultDetailPresenterTest {

    @Mock
    lateinit var service: MovieService

    @Mock
    lateinit var view: DefaultDetailPresenter.View

    private lateinit var presenter: DefaultDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DefaultDetailPresenter(view, service)
    }

    @Test
    fun test_onCreation_fetchMovieWithId(){
        val movie = Movie(1,1,false,1f,"",1f,"","","", arrayOf(), "", false, "","")

        `when`(service.getMovie("1")).thenReturn(Observable.just(movie))

        presenter.fetchMovie("1")

        verify(view).showMovie(movie)
    }

    @Test
    fun test_onCreation_fetchMovieWithId_handleErrorCase(){
        `when`(service.getMovie("1")).thenReturn(Observable.error(Exception("movie not found")))

        presenter.fetchMovie("1")

        verify(view).showError("movie not found")
    }
}