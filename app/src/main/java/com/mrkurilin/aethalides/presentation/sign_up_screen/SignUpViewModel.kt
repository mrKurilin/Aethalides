package com.mrkurilin.aethalides.presentation.sign_up_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavOptions
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R

class SignUpViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val navController = aethalidesApp.provideNavController()

    fun signInTextViewPressed() {
        navController.navigate(
            R.id.signInFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.signUpFragment, true).build()
        )
    }
}