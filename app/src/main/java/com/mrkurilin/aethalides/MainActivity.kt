package com.mrkurilin.aethalides

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mrkurilin.aethalides.ui.sign_in_screen.SignInFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SignInFragment.newInstance())
            .commitNow()
    }
}