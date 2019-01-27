package it.android.luca.movieapp.di

import dagger.Provides
import dagger.Module
import it.android.luca.movieapp.detail.presenter.DefaultDetailPresenter
import it.android.luca.movieapp.network.MovieService


@Module
class DetailModule(val view: DefaultDetailPresenter.View) {

    @Provides
    fun provideView(): DefaultDetailPresenter.View = view

    @Provides
    fun providePresenter(service: MovieService, view: DefaultDetailPresenter.View): DefaultDetailPresenter {
        return DefaultDetailPresenter(view, service)
    }
}