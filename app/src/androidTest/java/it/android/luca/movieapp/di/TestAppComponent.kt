package it.android.luca.movieapp.di

import android.app.Application
import dagger.Component
import it.android.luca.movieapp.network.MovieService
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(TestNetworkModule::class))
interface TestAppComponent: AppComponent {

    override fun movieService(): MovieService
override fun inject(application: Application)
}