package com.mrkurilin.aethalides.deleted_files.sign_in_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.databinding.FragmentSignInBinding
import com.mrkurilin.aethalides.util.hide
import com.mrkurilin.aethalides.util.show
import com.mrkurilin.aethalides.util.showLongToast
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private val viewModel by viewModels<SignInViewModel>()

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            val email = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.signInButtonPressed(email, password)
        }

        binding.registerTextView.setOnClickListener {
            val login = binding.loginEditText.text.toString()
            viewModel.registerTextViewPressed(login)
        }

        binding.signInWithGoogleButton.setOnClickListener {
            viewModel.signInWithGoogleButtonPressed()
        }

        lifecycleScope.launch {
            viewModel.states.collect(this@SignInFragment::updateUi)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateUi(state: SignInViewModel.State) {
        when (state) {
            SignInViewModel.State.Initial -> showLongToast(R.string.hello)
            SignInViewModel.State.Loading -> uiShowLoading()
            SignInViewModel.State.ErrorNoInternetConnection -> uiNoInternetConnection()
            SignInViewModel.State.SignInError -> uiSignInError()
        }
    }

    private fun uiShowLoading() {
        binding.signInGroup.hide()
        binding.progressBar.show()
    }

    private fun uiNoInternetConnection() {
        showLongToast(R.string.no_internet_connection)
    }

    private fun uiSignInError() {
        uiStopLoading()
        showLongToast(R.string.sign_in_error)
        binding.loginEditText.requestFocus()
    }

    private fun uiStopLoading() {
        binding.signInGroup.show()
        binding.progressBar.hide()
    }

    companion object {

        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }
}