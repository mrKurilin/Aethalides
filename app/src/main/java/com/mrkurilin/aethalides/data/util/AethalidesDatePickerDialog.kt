package com.mrkurilin.aethalides.data.util

import android.app.DatePickerDialog
import android.content.Context
import com.mrkurilin.aethalides.R
import java.time.LocalDate

class AethalidesDatePickerDialog {

    companion object {

        fun show(
            context: Context,
            localDate: LocalDate,
            onDateSet: (localDate: LocalDate) -> Unit,
        ) {
            val dialog = DatePickerDialog(
                context,
                R.style.MySpinnerDatePickerStyle,
                { _, setYear, setMonth, setDayOfMonth ->
                    onDateSet(LocalDate.of(setYear, setMonth + 1, setDayOfMonth))
                },
                localDate.year,
                localDate.monthValue - 1,
                localDate.dayOfMonth,
            )
            dialog.show()
        }
    }
}