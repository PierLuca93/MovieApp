package it.android.luca.movieapp.utils

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import it.android.luca.movieapp.di.TestApp

class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}