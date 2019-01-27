package it.android.luca.movieapp.di

import dagger.Component
import it.android.luca.movieapp.detail.ui.DetailActivity

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class),modules = arrayOf(DetailModule::class))
interface DetailComponent {

    fun inject(activity: DetailActivity)
}