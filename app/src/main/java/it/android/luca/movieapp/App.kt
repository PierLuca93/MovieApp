package it.android.luca.movieapp

import android.app.Application
import it.android.luca.movieapp.di.AppComponent
import it.android.luca.movieapp.di.DaggerAppComponent
import it.android.luca.movieapp.di.NetworkModule

open class App: Application() {
    lateinit var component:AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
        component.inject(this)
    }

    fun getAppComponent(): AppComponent? = component

}