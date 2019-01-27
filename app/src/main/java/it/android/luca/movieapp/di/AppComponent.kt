package it.android.luca.movieapp.di

import android.app.Application
import dagger.Component
import it.android.luca.movieapp.App
import it.android.luca.movieapp.network.MovieApi
import it.android.luca.movieapp.network.MovieService
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface AppComponent {

    fun movieService(): MovieService

    fun inject(application: Application)
}