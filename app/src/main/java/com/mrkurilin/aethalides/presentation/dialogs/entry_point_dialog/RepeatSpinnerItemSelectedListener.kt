package com.mrkurilin.aethalides.presentation.dialogs.entry_point_dialog

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener

class RepeatSpinnerItemSelectedListener(
    private val onItemSelectedOverride: () -> Unit
) : OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onItemSelectedOverride()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //do nothing
    }
}