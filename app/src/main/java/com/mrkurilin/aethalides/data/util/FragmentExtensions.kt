package com.mrkurilin.aethalides.data.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showLongToast(@StringRes stringId: Int) {
    Toast.makeText(requireContext(), stringId, Toast.LENGTH_LONG).show()
}

fun Fragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
}