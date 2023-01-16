package com.mrkurilin.aethalides.data.util

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showLongToast(@StringRes stringId: Int){
    Toast.makeText(requireContext(), stringId, Toast.LENGTH_LONG).show()
}