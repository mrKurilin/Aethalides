package com.mrkurilin.aethalides.data.util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class ItemsRecyclerView<T : Any, VH> : RecyclerView.Adapter<VH>()
        where VH : RecyclerView.ViewHolder, VH : Binding<T> {

    private var items: List<T> = listOf()

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
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