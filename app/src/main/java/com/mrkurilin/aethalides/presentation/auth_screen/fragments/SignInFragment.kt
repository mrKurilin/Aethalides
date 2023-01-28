package com.mrkurilin.aethalides.presentation.auth_screen.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.AuthCredential
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.hideKeyboard
import com.mrkurilin.aethalides.presentation.auth_screen.AuthViewModel
import kotlinx.coroutines.launch

class SignInFragment : AuthAbstractFragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModels<AuthViewModel>()

    private lateinit var signInButton: Button
    private lateinit var signUpTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        signInButton.setOnClickListener {
            hideKeyboard()
            tryToSignIn()
        }

        retryButton.setOnClickListener {
            tryToSignIn()
        }

        googleSignInButton.setOnClickListener {
            viewModel.googleSignInButtonPressed()
        }

        signUpTextView.setOnClickListener {
            viewModel.signUpTextViewPressed()
        }

        lifecycleScope.launch {
            viewModel.uiState.collect(this@SignInFragment::updateUi)
        }
    }

    override fun onGoogleSignInActivityResult(credential: AuthCredential?) {
        viewModel.signInWithCredential(credential)
    }

    private fun tryToSignIn() {
        val email = emailEditTextView.text.toString()
        val password = passwordEditTextView.text.toString()
        viewModel.tryToSignIn(email, password)
    }

    override fun initViews() {
        val view = requireView()
        emailEditTextView = view.findViewById(R.id.email_edit_text)
        passwordEditTextView = view.findViewById(R.id.password_edit_text)
        signInButton = view.findViewById(R.id.sign_in_button)
        googleSignInButton = view.findViewById(R.id.google_sign_in_button)
        signUpTextView = view.findViewById(R.id.sign_up_text_view)
        progressBar = view.findViewById(R.id.progress_bar)
        mainUiGroup = view.findViewById(R.id.sign_in_group)
        noNetworkErrorGroup = view.findViewById(R.id.no_network_error_group)
        retryButton = view.findViewById(R.id.no_network_image_button)
    }
}