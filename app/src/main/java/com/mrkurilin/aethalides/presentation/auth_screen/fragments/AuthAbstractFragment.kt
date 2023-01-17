package com.mrkurilin.aethalides.presentation.auth_screen.fragments

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.AuthCredential
import com.mrkurilin.aethalides.presentation.auth_screen.AuthUiState
import com.mrkurilin.aethalides.data.util.setErrorIfEmpty
import com.mrkurilin.aethalides.data.util.showLongToast
import com.mrkurilin.aethalides.presentation.auth_screen.GoogleSignInActivityResultContract

abstract class AuthAbstractFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected lateinit var mainUiGroup: Group
    protected lateinit var noNetworkErrorGroup: Group
    protected lateinit var progressBar: ProgressBar
    protected lateinit var emailEditTextView: EditText
    protected lateinit var googleSignInButton: SignInButton
    protected lateinit var retryButton: ImageButton
    protected lateinit var passwordEditTextView: EditText

    private lateinit var launcher: ActivityResultLauncher<Activity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launcher = registerForActivityResult(GoogleSignInActivityResultContract()) { credential ->
            onGoogleSignInActivityResult(credential)
        }

        requireActivity().actionBar?.hide()
    }

    protected abstract fun initViews()
    protected abstract fun onGoogleSignInActivityResult(credential: AuthCredential?)

    protected fun updateUi(uiState: AuthUiState) {
        when (uiState) {
            AuthUiState.Initial -> {
                uiShowMainUi()
            }
            AuthUiState.Loading -> {
                uiShowLoading()
            }
            AuthUiState.GoogleSignIn -> {
                uiShowLoading()
                launcher.launch(requireActivity())
            }
            AuthUiState.NoNetworkError -> {
                uiShowNoNetworkError()
            }
            is AuthUiState.ToastError -> {
                uiShowMainUi()
                showLongToast(uiState.getDescription())
                setErrorToEmptyEditTexViews()
            }
            AuthUiState.GoogleSignInCanceled -> {
                uiShowMainUi()
            }
        }
    }

    private fun uiShowLoading() {
        progressBar.isVisible = true
        mainUiGroup.isVisible = false
        noNetworkErrorGroup.isVisible = false
    }

    private fun uiShowMainUi() {
        progressBar.isVisible = false
        mainUiGroup.isVisible = true
        noNetworkErrorGroup.isVisible = false
    }

    private fun uiShowNoNetworkError() {
        progressBar.isVisible = false
        mainUiGroup.isVisible = false
        noNetworkErrorGroup.isVisible = true
    }

    private fun setErrorToEmptyEditTexViews() {
        (requireView() as ConstraintLayout).children.forEach { view ->
            if (view is EditText) {
                view.setErrorIfEmpty()
            }
        }
    }
}