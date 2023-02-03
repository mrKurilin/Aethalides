package com.mrkurilin.aethalides.data.util

import android.app.TimePickerDialog
import android.content.Context
import com.mrkurilin.aethalides.R
import java.time.LocalTime

class AethalidesTimePickerDialog {

    companion object {

        fun show(
            context: Context,
            localTime: LocalTime,
            onTimeSet: (LocalTime) -> Unit,
        ) {
            val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                onTimeSet(LocalTime.of(hourOfDay, minute))
            }
            val dialog = TimePickerDialog(
                context,
                R.style.MySpinnerTimePickerStyle,
                onTimeSetListener,
                localTime.hour,
                localTime.minute,
                true
            )
            dialog.show()
        }
    }
}