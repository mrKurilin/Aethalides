package com.mrkurilin.aethalides.presentation.sign_in_screen

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.SignInButton
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.hideKeyboard
import com.mrkurilin.aethalides.data.util.setErrorIfEmpty
import com.mrkurilin.aethalides.data.util.showLongToast
import kotlinx.coroutines.launch

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModels<SignInViewModel>()

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var googleSignInButton: SignInButton
    private lateinit var signUpTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var signInGroup: Group
    private lateinit var noNetworkErrorGroup: Group
    private lateinit var retrySignInImageButton: ImageButton

    private lateinit var launcher: ActivityResultLauncher<Activity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launcher = registerForActivityResult(GoogleSignInActivityResultContract()) { credential ->
            uiStopLoading()
            viewModel.signInWithCredential(credential)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        signInButton.setOnClickListener {
            signInButtonPressed()
        }

        retrySignInImageButton.setOnClickListener {
            signInButtonPressed()
        }

        googleSignInButton.setOnClickListener {
            uiShowLoading()
            launcher.launch(requireActivity())
        }

        signUpTextView.setOnClickListener {
            viewModel.signUpTextViewPressed()
        }

        lifecycleScope.launch {
            viewModel.uiState.collect(this@SignInFragment::updateUi)
        }
    }

    private fun signInButtonPressed() {
        hideKeyboard()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        viewModel.signInButtonPressed(email, password)
    }

    private fun updateUi(uiState: SignInViewModel.UiState) {
        when (uiState) {
            is SignInViewModel.UiState.Initial -> {
                uiStopLoading()
            }
            is SignInViewModel.UiState.Loading -> {
                uiShowLoading()
            }
            is SignInViewModel.UiState.ToastError -> {
                uiStopLoading()
                showLongToast(uiState.getDescription())
                emailEditText.setErrorIfEmpty()
                passwordEditText.setErrorIfEmpty()
            }
            is SignInViewModel.UiState.NoNetworkError -> {
                uiShowNoNetworkError()
            }
        }
    }

    private fun uiShowLoading() {
        progressBar.isVisible = true
        signInGroup.isVisible = false
        noNetworkErrorGroup.isVisible = false
    }

    private fun uiStopLoading() {
        progressBar.isVisible = false
        signInGroup.isVisible = true
    }

    private fun uiShowNoNetworkError() {
        noNetworkErrorGroup.isVisible = true
        progressBar.isVisible = false
        signInGroup.isVisible = false
    }

    private fun initViews() {
        val view = requireView()
        emailEditText = view.findViewById(R.id.email_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        signInButton = view.findViewById(R.id.sign_in_button)
        googleSignInButton = view.findViewById(R.id.google_sign_in_button)
        signUpTextView = view.findViewById(R.id.sign_up_text_view)
        progressBar = view.findViewById(R.id.progress_bar)
        signInGroup = view.findViewById(R.id.sign_in_group)
        noNetworkErrorGroup = view.findViewById(R.id.no_network_error_group)
        retrySignInImageButton = view.findViewById(R.id.no_network_image_button)
    }
}