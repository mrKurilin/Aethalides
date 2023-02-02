package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmptyAdapterObserver(
    private val headerTextView: TextView,
    private val recyclerView: RecyclerView,
    private val noDataImageView: ImageView
) : RecyclerView.AdapterDataObserver() {

    init {
        checkData()
    }

    private fun checkData(){
       val isRecyclerViewEmpty = (recyclerView.adapter?.itemCount ?: 0) == 0
        headerTextView.visibility = if (isRecyclerViewEmpty) View.GONE else View.VISIBLE
        recyclerView.visibility = if (isRecyclerViewEmpty) View.GONE else View.VISIBLE
        noDataImageView.visibility = if (isRecyclerViewEmpty) View.VISIBLE else View.GONE
    }

    override fun onChanged() {
        checkData()
    }
}