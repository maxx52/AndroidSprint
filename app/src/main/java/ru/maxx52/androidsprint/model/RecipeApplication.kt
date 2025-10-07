package ru.maxx52.androidsprint.model

import android.app.Application

class RecipeApplication : Application() {
    companion object {
        lateinit var instance: RecipeApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}