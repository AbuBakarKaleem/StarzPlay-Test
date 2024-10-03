package com.starzplay.entertainment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StarzPlay : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: StarzPlay? = null
        fun getInstance(): StarzPlay {
            synchronized(StarzPlay::class.java) {
                if (instance == null) instance = StarzPlay()
            }
            return instance!!
        }
    }
}