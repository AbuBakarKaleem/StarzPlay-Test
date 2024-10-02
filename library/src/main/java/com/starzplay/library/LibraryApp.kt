package com.starzplay.library

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LibraryApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: LibraryApp? = null
        fun getInstance(): LibraryApp {
            synchronized(LibraryApp::class.java) {
                if (instance == null) instance = LibraryApp()
            }
            return instance!!
        }
    }
}