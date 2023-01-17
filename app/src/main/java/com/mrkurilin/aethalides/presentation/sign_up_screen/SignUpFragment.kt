package com.mrkurilin.aethalides.presentation.sign_up_screen

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mrkurilin.aethalides.R

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()

    private lateinit var signInTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        signInTextView.setOnClickListener {
            viewModel.signInTextViewPressed()
        }
    }

    private fun initViews() {
        val view = requireView()
        signInTextView = view.findViewById(R.id.sign_in_text_view)
    }

}