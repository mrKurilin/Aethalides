package com.mrkurilin.aethalides.data.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
        return _binding!!.root
    }

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): T

    override fun onResume() {
        val metrics = resources.displayMetrics
        requireView().layoutParams = FrameLayout.LayoutParams(
            (metrics.widthPixels * 0.90).toInt(),
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        super.onResume()
    }
}