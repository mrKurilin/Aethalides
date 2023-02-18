package com.mrkurilin.aethalides.data.util

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mrkurilin.aethalides.AethalidesApp
import kotlinx.coroutines.flow.MutableStateFlow

abstract class EntryItemAndroidViewModel<T>(
    app: Application
) : AndroidViewModel(app) {

    protected val aethalidesApp = app as AethalidesApp
    val entryStateFlow: MutableStateFlow<EntryState> = MutableStateFlow(EntryState.Add)
    protected var valueToEdit: T? = null

    fun checkArgsValue(value: T?) {
        if (value != null) {
            valueToEdit = value
            entryStateFlow.value = EntryState.Edit
        }
    }
}