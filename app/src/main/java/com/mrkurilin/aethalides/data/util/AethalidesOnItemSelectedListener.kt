package com.mrkurilin.aethalides.data.util

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener

class AethalidesOnItemSelectedListener(
    private val onItemSelectedOverride: (
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) -> Unit,
) : OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onItemSelectedOverride(parent, view, position, id)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
//        do nothing
    }
}