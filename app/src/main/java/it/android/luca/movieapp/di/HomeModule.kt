package it.android.luca.movieapp.di

import dagger.Provides
import dagger.Module
import it.android.luca.movieapp.home.presenter.DefaultHomePresenter
import it.android.luca.movieapp.home.presenter.HomePresenter
import it.android.luca.movieapp.network.MovieService
import javax.inject.Singleton


@Module
class HomeModule(val view: DefaultHomePresenter.View) {

    @Provides
    fun provideView(): DefaultHomePresenter.View = view

    @Provides
    fun providePresenter(service: MovieService, view: DefaultHomePresenter.View): HomePresenter {
        return DefaultHomePresenter(service, view)
    }
}