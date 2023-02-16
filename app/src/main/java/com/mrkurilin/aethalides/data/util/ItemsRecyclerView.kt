package com.mrkurilin.aethalides.data.util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class ItemsRecyclerView<T : Any, VH : RecyclerView.ViewHolder>(

) : RecyclerView.Adapter<VH>() {

    private var items: List<T> = listOf()

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder is Binding) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<T>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilCallback(
                this.items,
                items,
            )
        )
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }
}