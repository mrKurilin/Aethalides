package com.mrkurilin.aethalides.presentation.sign_in_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val navController = aethalidesApp.provideNavController()

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    val uiState: StateFlow<UiState> = _uiState

    private val auth = Firebase.auth

    fun signInButtonPressed(email: String, password: String) {
        _uiState.value = UiState.Loading
        if (emailAndPasswordAreValid(email, password)) {
            signInWithEmailAndPassword(email, password)
        } else {
            _uiState.value = UiState.ErrorEmptyFields
        }
    }

    private fun emailAndPasswordAreValid(email: String, password: String): Boolean {
        return (email.isNotBlank() && password.isNotBlank())
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        val signInTask = auth.signInWithEmailAndPassword(email, password)
        signInTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate(R.id.action_signInFragment_to_mainFragment)
            } else {
                _uiState.value = UiState.WrongEmailOrPassword
            }
        }
            .addOnFailureListener { exception ->
                if (exception.message.equals("NO_NETWORK")) {
                    _uiState.value = UiState.ErrorNoInternetConnection
                }
            }
    }

    fun googleSignInButtonPressed() {
        TODO("Not yet implemented")
    }

    fun signUpTextViewPressed() {
        TODO("Not yet implemented")
    }

    sealed class UiState {
        object Init : UiState()
        object Loading : UiState()
        object WrongEmailOrPassword : UiState()
        object ErrorNoInternetConnection : UiState()
        object ErrorEmptyFields : UiState()
    }
}