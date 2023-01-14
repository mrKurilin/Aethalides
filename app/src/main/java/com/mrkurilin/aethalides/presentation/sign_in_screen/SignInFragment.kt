package com.mrkurilin.aethalides.presentation.sign_in_screen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.SignInButton
import com.mrkurilin.aethalides.R
import kotlinx.coroutines.launch

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModels<SignInViewModel>()

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var googleSignInButton: SignInButton
    private lateinit var signUpTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.signInButtonPressed(email, password)
        }

        googleSignInButton.setOnClickListener {
            viewModel.googleSignInButtonPressed()
        }

        signUpTextView.setOnClickListener {
            viewModel.signUpTextViewPressed()
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                updateUi(uiState)
            }
        }
    }

    private fun updateUi(uiState: SignInViewModel.UiState) {
        when (uiState) {
            SignInViewModel.UiState.Init -> {
                //do nothing
            }
            SignInViewModel.UiState.Loading -> {
                showLoading()
            }
            SignInViewModel.UiState.WrongEmailOrPassword -> {
                // TODO: show error
            }
            SignInViewModel.UiState.ErrorNoInternetConnection -> {
                Toast.makeText(requireContext(), "FUCK YEAH", Toast.LENGTH_LONG).show()
                // TODO: show error
            }
            SignInViewModel.UiState.ErrorEmptyFields -> {
                uiShowFieldsCanNotBeEmptyError()
            }
        }
    }

    private fun uiShowFieldsCanNotBeEmptyError() {
        if (emailEditText.text.toString().isEmpty()) {
            emailEditText.error = "Field can not be empty"
        }
        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = "Field can not be empty"
        }
    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    private fun initViews() {
        val view = requireView()
        emailEditText = view.findViewById(R.id.email_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        signInButton = view.findViewById(R.id.sign_in_button)
        googleSignInButton = view.findViewById(R.id.google_sign_in_button)
        signUpTextView = view.findViewById(R.id.sign_up_text_view)
    }
}