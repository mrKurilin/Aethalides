package com.mrkurilin.aethalides.presentation.auth_screen

import androidx.annotation.StringRes

sealed class AuthUiState {

    object Initial : AuthUiState()
    object Loading : AuthUiState()
    object NoNetworkError : AuthUiState()
    object GoogleSignIn : AuthUiState()
    object GoogleSignInCanceled : AuthUiState()
    object SignedIn : AuthUiState()

    data class ToastError(@StringRes private val errorDescription: Int) : AuthUiState() {

        fun getDescription(): Int {
            return errorDescription
        }
    }
}