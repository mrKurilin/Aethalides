package com.mrkurilin.aethalides.presentation.sign_in_screen

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavOptions
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val navController = aethalidesApp.provideNavController()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState

    fun tryToSignIn(email: String, password: String) {
        _uiState.value = UiState.Loading
        try {
            signInWithEmailAndPassword(email, password)
        } catch (e: Exception) {
            handleSignInTaskException(e)
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        val signInTask = Firebase.auth.signInWithEmailAndPassword(email, password)
        signInTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToMainFragment()
            } else {
                handleSignInTaskException(task.exception)
            }
        }
    }

    private fun handleSignInTaskException(exception: Exception?) {
        when (exception) {
            is FirebaseNetworkException -> {
                _uiState.value = UiState.NoNetworkError
            }
            is IllegalArgumentException -> {
                _uiState.value = UiState.ToastError(R.string.error_fields_can_not_be_empty)
            }
            is FirebaseAuthInvalidCredentialsException -> {
                _uiState.value = UiState.ToastError(R.string.error_wrong_email_or_password)
            }
            else -> {
                throw Throwable(exception)
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
        } catch (e: Exception) {
            handleSignInTaskException(e)
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

    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()

        class ToastError(@StringRes private val errorDescription: Int) : UiState() {

            fun getDescription(): Int {
                return errorDescription
            }
        }

        object NoNetworkError : UiState()
    }
}