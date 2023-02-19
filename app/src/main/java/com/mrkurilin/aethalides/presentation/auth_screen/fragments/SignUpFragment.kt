package com.mrkurilin.aethalides.presentation.auth_screen.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.AuthCredential
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.hideKeyboard
import com.mrkurilin.aethalides.presentation.auth_screen.AuthViewModel
import kotlinx.coroutines.launch

class SignUpFragment : AuthAbstractFragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModels<AuthViewModel>()

    private lateinit var repeatPasswordEditTextView: TextView
    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setListeners()

        lifecycleScope.launch {
            viewModel.uiState.collect(this@SignUpFragment::updateUi)
        }
    }

    override fun onGoogleSignInActivityResult(credential: AuthCredential?) {
        viewModel.signInWithCredential(credential)
    }

    private fun setPasswordEditTextViewsNotEqualsError() {
        repeatPasswordEditTextView.error = getString(R.string.passwords_are_not_equals)
    }

    private fun tryToSignUp() {
        val email = emailEditTextView.text.toString()
        val password = passwordEditTextView.text.toString()
        viewModel.tryToSignUp(email, password)
    }

    private fun passwordsAreEquals(): Boolean {
        return passwordEditTextView.text.toString() == repeatPasswordEditTextView.text.toString()
    }

    override fun initViews() {
        val view = requireView()
        emailEditTextView = view.findViewById(R.id.email_edit_text)
        passwordEditTextView = view.findViewById(R.id.password_edit_text)
        repeatPasswordEditTextView = view.findViewById(R.id.repeat_password_edit_text)
        signUpButton = view.findViewById(R.id.sign_up_button)
        googleSignInButton = view.findViewById(R.id.google_sign_in_button)
        signInTextView = view.findViewById(R.id.sign_in_text_view)
        progressBar = view.findViewById(R.id.progress_bar)
        retryButton = view.findViewById(R.id.no_network_image_button)
        mainUiGroup = view.findViewById(R.id.sign_up_group)
        noNetworkErrorGroup = view.findViewById(R.id.no_network_error_group)
    }

    override fun setListeners() {
        signUpButton.setOnClickListener {
            hideKeyboard()
            if (passwordsAreEquals()) {
                tryToSignUp()
            } else {
                setPasswordEditTextViewsNotEqualsError()
            }
        }

        googleSignInButton.setOnClickListener {
            viewModel.googleSignInButtonPressed()
        }

        retryButton.setOnClickListener {
            tryToSignUp()
        }

        signInTextView.setOnClickListener {
            findNavController().navigate(
                R.id.sign_in_fragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.signUpFragment, true).build()
            )
        }
    }
}