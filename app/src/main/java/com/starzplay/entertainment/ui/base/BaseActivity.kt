package com.starzplay.entertainment.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController


abstract class BaseActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        print("onCreate Called")
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        print("onStart Called")
        super.onStart()
    }

    override fun onResume() {
        print("onResume Called")
        super.onResume()
    }

    override fun onPause() {
        print("onPause Called")
        super.onPause()
    }

    override fun onStop() {
        print("onStop Called")
        super.onStop()
    }

    override fun onRestart() {
        print("onRestart Called")
        super.onRestart()
    }

    override fun onDestroy() {
        print("onDestroy Called")
        super.onDestroy()
    }
}
