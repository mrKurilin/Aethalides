package com.mrkurilin.aethalides.util

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showLongToast(@StringRes stringId: Int) {
    val text = resources.getString(stringId)
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}

fun Fragment.showShortToast(@StringRes stringId: Int) {
    val text = resources.getString(stringId)
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}