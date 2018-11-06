package it.android.luca.movieapp.di

import dagger.Provides
import dagger.Module
import it.android.luca.movieapp.home.presenter.DefaultHomePresenter
import it.android.luca.movieapp.network.MovieService


@Module
class HomeModule(val view: DefaultHomePresenter.View) {

    @Provides
    fun provideView(): DefaultHomePresenter.View = view

    @Provides
    fun providePresenter(service: MovieService, view: DefaultHomePresenter.View): DefaultHomePresenter {
        return DefaultHomePresenter(service, view)
    }
}