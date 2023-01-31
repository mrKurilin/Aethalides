package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoDataRecyclerViewObserver(
    private val recyclerView: RecyclerView,
    private val headerTextView: TextView,
    private val emptyView: ImageView
) : RecyclerView.AdapterDataObserver() {

    init {
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        val emptyViewVisible = (recyclerView.adapter?.itemCount ?: 0) == 0
        headerTextView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        recyclerView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        emptyView.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }
}