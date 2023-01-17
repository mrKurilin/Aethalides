package com.mrkurilin.aethalides.data.util

import android.widget.EditText
import com.mrkurilin.aethalides.R

fun EditText.setErrorIfEmpty() {
    if (this.text.toString().isEmpty()) {
        this.error = context.getString(R.string.field_can_not_be_empty)
    }
}