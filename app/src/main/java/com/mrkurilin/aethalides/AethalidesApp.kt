package com.mrkurilin.aethalides

import android.app.Application
import androidx.navigation.NavController

class AethalidesApp : Application() {

    private lateinit var navController: NavController

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun provideNavController(): NavController {
        return navController
    }
}