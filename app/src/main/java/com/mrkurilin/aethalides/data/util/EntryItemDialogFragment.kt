package com.mrkurilin.aethalides.data.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class EntryItemDialogFragment<T : ViewBinding>(
    @LayoutRes
    private val layoutId: Int,
) : DialogFragment() {

    private var _binding: T? = null
    protected val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = initBinding(inflater, container)
        val view = inflater.inflate(layoutId, container, false)
        val displayWidth = resources.displayMetrics.widthPixels
        view.layoutParams = ViewGroup.LayoutParams(
            (displayWidth * 0.95).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return view
    }

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): T
}