package com.mrkurilin.aethalides.data.util

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterDataObserver(
    private val placeholder: View,
    private val recyclerView: RecyclerView
) : RecyclerView.AdapterDataObserver() {

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        onChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        onChanged()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        onChanged()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        onChanged()
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        onChanged()
    }

    override fun onChanged() {
        placeholder.isVisible = isRecyclerViewAdapterEmpty()
        recyclerView.isVisible = !isRecyclerViewAdapterEmpty()
    }

    private fun isRecyclerViewAdapterEmpty(): Boolean {
        return (recyclerView.adapter?.itemCount ?: 0) == 0
    }
}