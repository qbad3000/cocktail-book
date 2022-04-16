package com.gmail.domanskiquba.android.cocktailbook

import android.app.Application

class CocktailBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CocktailRepository.initialize(this)
    }
}