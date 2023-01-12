package com.mrkurilin.aethalides.deleted_files.sign_in_screen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SignInViewModel : ViewModel() {

    val states: Flow<State> get() = _states
    private val _states = MutableStateFlow<State>(State.Initial)
    private val firebaseAuth = Firebase.auth

    fun signInButtonPressed(email: String, password: String) {
        _states.value = State.Loading
        val signInTask = firebaseAuth.signInWithEmailAndPassword(email, password)
        signInTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // TODO: Navigate to MainFragment
            } else {

            }
        }
    }

    fun registerTextViewPressed(login: String) {
        // TODO: Navigate to SignUpFragment
    }

    fun signInWithGoogleButtonPressed() {

    }

    sealed class State {
        object Initial : State()
        object Loading : State()
        object SignInError : State()
        object ErrorNoInternetConnection : State()
    }
}