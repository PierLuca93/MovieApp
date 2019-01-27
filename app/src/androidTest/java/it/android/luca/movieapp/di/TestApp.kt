package it.android.luca.movieapp.di

import it.android.luca.movieapp.App


class TestApp: App() {
//    lateinit var component: TestAppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerTestAppComponent.builder()
            .testNetworkModule(TestNetworkModule())
            .build()
        component.inject(this)
    }

//    fun getAppComponent(): TestAppComponent? = component

}