package com.mrkurilin.aethalides.presentation.dialogs.entry_spending_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.*
import com.mrkurilin.aethalides.databinding.DialogEntrySpendingBinding
import kotlinx.coroutines.launch
import java.time.LocalDate

class EntrySpendingDialogFragment : EntryItemDialogFragment<DialogEntrySpendingBinding>(
    R.layout.dialog_entry_spending
) {

    private val viewModel by viewModels<EntrySpendingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeFlows()

        setListeners()

        val epochDay = arguments?.getLong(
            NavigationConstants.EPOCH_DAY_KEY
        ) ?: LocalDate.now().toEpochDay()
        viewModel.currentLocalDateFlow.value = LocalDate.ofEpochDay(epochDay)
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            viewModel.currentLocalDateFlow.collect { localDate ->
                binding.datePickerTextView.text = LocalDateUtil.localDateToString(localDate)
            }
        }

        lifecycleScope.launch {
            viewModel.currentLocalTimeFlow.collect { localTime ->
                binding.timePickerTextView.text = LocalTimeUtil.toString(localTime)
            }
        }
    }

    private fun setListeners() {
        binding.datePickerTextView.setOnClickListener {
            AethalidesDatePickerDialog.show(
                context = requireContext(),
                localDate = viewModel.currentLocalDateFlow.value,
                onDateSet = { localDate ->
                    viewModel.currentLocalDateFlow.value = localDate
                }
            )
        }

        binding.timePickerTextView.setOnClickListener {
            AethalidesTimePickerDialog.show(
                context = requireContext(),
                localTime = viewModel.currentLocalTimeFlow.value,
                onTimeSet = { localTime ->
                    viewModel.currentLocalTimeFlow.value = localTime
                }
            )
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.doneButton.setOnClickListener {
            viewModel.addSpending(
                name = binding.nameEditText.text.toString(),
                amount = binding.amountEditText.text.toString().toFloat(),
                measure = binding.measureSpinner.selectedItem.toString(),
                cost = binding.countEditTextView.text.toString().toFloat(),
            )
            dismiss()
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEntrySpendingBinding {
        return DialogEntrySpendingBinding.inflate(inflater, container, false)
    }
}