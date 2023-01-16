package com.mrkurilin.aethalides.data.util

import android.widget.EditText

fun EditText.setErrorIfEmpty(){
    if (this.text.toString().isEmpty()) {
        this.error = "Field can not be empty"
    }
}