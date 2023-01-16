package com.mrkurilin.aethalides.presentation.sign_in_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val navController = aethalidesApp.provideNavController()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

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
        val signInTask = Firebase.auth.signInWithEmailAndPassword(email, password)
        signInTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToMainFragment()
            } else {
                _uiState.value = UiState.WrongEmailOrPassword
            }
        }
    }

    fun signUpTextViewPressed() {
        navController.navigate(
            R.id.signUpFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.signInFragment, true).build()
        )
    }

    fun signInWithCredential(credential: AuthCredential?) {
        if (credential == null) {
            return
        }

        try {
            Firebase.auth.signInWithCredential(credential)
        } catch (e: FirebaseAuthInvalidUserException) {

        } catch (e: FirebaseAuthInvalidCredentialsException) {

        } catch (e: FirebaseAuthUserCollisionException) {

        } catch (e: Exception) {

        }

        navigateToMainFragment()
    }

    private fun navigateToMainFragment() {
        navController.navigate(
            R.id.mainFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.signInFragment, true).build()
        )
    }

    fun viewCreated() {
        _uiState.value = UiState.ViewCreated
    }

    sealed class UiState {
        object ViewCreated : UiState()
        object Loading : UiState()
        object WrongEmailOrPassword : UiState()
        object ErrorNoInternetConnection : UiState()
        object ErrorEmptyFields : UiState()
    }
}