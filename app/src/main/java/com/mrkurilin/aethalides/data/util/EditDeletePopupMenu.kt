package com.mrkurilin.aethalides.data.util

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.mrkurilin.aethalides.R

class EditDeletePopupMenu<T>(
    context: Context,
    anchor: View,
    private val item: T,
    private val startEditItem: (T) -> Unit,
    private val deleteItem: (T) -> Unit
) : PopupMenu(context, anchor) {

    init {
        inflate(R.menu.edit_delete_popup_menu)

        setOnMenuItemClickListener { menuItem ->
            handleMenuItem(menuItem)
            return@setOnMenuItemClickListener true
        }
    }

    private fun handleMenuItem(menuItem: MenuItem){
        when (menuItem.itemId) {
            R.id.edit -> {
                startEditItem(item)
            }
            R.id.delete -> {
                deleteItem(item)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }
}