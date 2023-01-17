package com.mrkurilin.aethalides.presentation.auth_screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.mrkurilin.aethalides.R

class GoogleSignInActivityResultContract : ActivityResultContract<Activity, AuthCredential?>() {

    override fun createIntent(context: Context, input: Activity): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(input.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(input, gso)
        return mGoogleSignInClient.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): AuthCredential? {
        return try {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val googleSignInAccount = task.getResult(ApiException::class.java)
            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        } catch (e: Exception) {
            null
        }
    }
}