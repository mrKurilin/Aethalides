package com.mrkurilin.aethalides.presentation.dialogs.entry_spending_dialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.data.model.Spending
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

class EntrySpendingViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val spendingRepository = aethalidesApp.provideSpendingRepository()
    var currentLocalDateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    var currentLocalTimeFlow: MutableStateFlow<LocalTime> = MutableStateFlow(LocalTime.now())

    fun addSpending(
        name: String,
        amount: Float,
        measure: String,
        cost: Float,
    ) {
        spendingRepository.addSpending(
            Spending(
                name = name,
                amount = amount,
                measure = measure,
                cost = cost,
                utcEpochSecond = currentLocalTimeFlow.value.toEpochSecond(
                    currentLocalDateFlow.value,
                    ZoneOffset.UTC
                ),
                epochDay = currentLocalDateFlow.value.toEpochDay()
            )
        )
    }
}