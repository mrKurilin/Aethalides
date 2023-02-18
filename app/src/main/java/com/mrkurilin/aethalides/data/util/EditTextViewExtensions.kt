package com.mrkurilin.aethalides.data.util

import android.widget.EditText
import com.mrkurilin.aethalides.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun EditText.setErrorIfEmpty() {
    if (this.text.toString().isEmpty()) {
        this.error = context.getString(R.string.field_can_not_be_empty)
    }
}

fun EditText.toLocalDate(): LocalDate {
    return LocalDate.parse(
        this.text.toString(),
        DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    )
}