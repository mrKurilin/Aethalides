package com.mrkurilin.aethalides.presentation.auth_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val navController = aethalidesApp.provideNavController()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState

    private val auth = Firebase.auth

    fun tryToSignIn(email: String, password: String) {
        _uiState.value = AuthUiState.Loading
        try {
            signInWithEmailAndPassword(email, password)
        } catch (e: Exception) {
            handleAuthException(e)
        }
    }

    fun signInWithCredential(credential: AuthCredential?) {
        if (credential == null) {
            _uiState.value = AuthUiState.GoogleSignInCanceled
            return
        }

        try {
            auth.signInWithCredential(credential)
        } catch (e: Exception) {
            handleAuthException(e)
        }

        navigateToMainFragment()
    }

    private fun handleAuthException(exception: Exception?) {
        when (exception) {
            is FirebaseNetworkException -> {
                _uiState.value = AuthUiState.NoNetworkError
            }
            is IllegalArgumentException -> {
                _uiState.value = AuthUiState.ToastError(R.string.error_fields_can_not_be_empty)
            }
            is FirebaseAuthInvalidCredentialsException -> {
                _uiState.value = AuthUiState.ToastError(R.string.error_wrong_email_or_password)
            }
            else -> {
                throw Throwable(exception)
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        val signInTask = auth.signInWithEmailAndPassword(email, password)
        handleAuthTask(signInTask)

    }

    private fun handleAuthTask(authTask: Task<AuthResult>) {
        authTask.addOnCompleteListener { completedTask ->
            if (completedTask.isSuccessful) {
                navigateToMainFragment()
            } else {
                handleAuthException(completedTask.exception)
            }
        }
    }

    private fun signUpWithEmailAndPassword(email: String, password: String) {
        val signUpTask = auth.createUserWithEmailAndPassword(email, password)
        handleAuthTask(signUpTask)
    }

    fun googleSignInButtonPressed() {
        _uiState.value = AuthUiState.GoogleSignIn
    }

    private fun navigateToMainFragment() {
        val currentFragmentId = navController.currentDestination?.id ?: throw RuntimeException()
        navController.navigate(
            R.id.mainFragment,
            null,
            NavOptions.Builder().setPopUpTo(currentFragmentId, true).build()
        )
    }

    fun signUpTextViewPressed() {
        navController.navigate(
            R.id.signUpFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.signInFragment, true).build()
        )
    }

    fun tryToSignUp(email: String, password: String) {
        _uiState.value = AuthUiState.Loading
        try {
            signUpWithEmailAndPassword(email, password)
        } catch (e: Exception) {
            handleAuthException(e)
        }
    }

    fun signInTextViewPressed() {
        navController.navigate(
            R.id.signInFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.signUpFragment, true).build()
        )
    }
}