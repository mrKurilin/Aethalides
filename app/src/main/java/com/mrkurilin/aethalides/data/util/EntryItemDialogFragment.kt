package com.mrkurilin.aethalides.data.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

open class EntryItemDialogFragment(
    @LayoutRes
    private val layoutId: Int,
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(layoutId, container, false)
        val displayWidth = resources.displayMetrics.widthPixels
        view.layoutParams = ViewGroup.LayoutParams(
            (displayWidth * 0.95).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return view
    }
}