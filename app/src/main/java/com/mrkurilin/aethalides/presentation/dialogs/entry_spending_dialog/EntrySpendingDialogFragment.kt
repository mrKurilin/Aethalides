package com.mrkurilin.aethalides.presentation.dialogs.entry_spending_dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.AethalidesDatePickerDialog
import com.mrkurilin.aethalides.data.util.AethalidesTimePickerDialog
import com.mrkurilin.aethalides.data.util.LocalDateUtil
import com.mrkurilin.aethalides.data.util.LocalTimeUtil
import kotlinx.coroutines.launch

class EntrySpendingDialogFragment : DialogFragment(R.layout.dialog_entry_spending) {

    private val viewModel by viewModels<EntrySpendingViewModel>()

    private lateinit var headerTextView: TextView
    private lateinit var spendingNameEditText: EditText
    private lateinit var spendingAmountEditText: EditText
    private lateinit var spendingMeasureSpinner: Spinner
    private lateinit var spendingCountEditText: EditText
    private lateinit var datePickerTextView: TextView
    private lateinit var timePickerTextView: TextView
    private lateinit var cancelButton: Button
    private lateinit var doneButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        observeFlows()
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            viewModel.currentLocalDateFlow.collect { localDate ->
                datePickerTextView.text = LocalDateUtil.localDateToString(localDate)
            }
        }

        lifecycleScope.launch {
            viewModel.currentLocalTimeFlow.collect { localTime ->
                timePickerTextView.text = LocalTimeUtil.toString(localTime)
            }
        }
    }

    private fun initViews() {
        val view = requireView()
        headerTextView = view.findViewById(R.id.header_text_view)
        spendingNameEditText = view.findViewById(R.id.spending_name_edit_text)
        spendingAmountEditText = view.findViewById(R.id.amount_edit_text)
        spendingMeasureSpinner = view.findViewById(R.id.amount_spinner)
        spendingCountEditText = view.findViewById(R.id.count_edit_text_view)
        datePickerTextView = view.findViewById(R.id.date_picker_text_view)
        timePickerTextView = view.findViewById(R.id.time_picker_text_view)
        cancelButton = view.findViewById(R.id.cancel_button)
        doneButton = view.findViewById(R.id.done_button)

        setListeners()
    }

    private fun setListeners() {
        datePickerTextView.setOnClickListener {
            AethalidesDatePickerDialog.show(
                context = requireContext(),
                localDate = viewModel.currentLocalDateFlow.value,
                onDateSet = { localDate ->
                    viewModel.currentLocalDateFlow.value = localDate
                }
            )
        }

        timePickerTextView.setOnClickListener {
            AethalidesTimePickerDialog.show(
                context = requireContext(),
                localTime = viewModel.currentLocalTimeFlow.value,
                onTimeSet = { localTime ->
                    viewModel.currentLocalTimeFlow.value = localTime
                }
            )
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        doneButton.setOnClickListener {
            viewModel.addSpending(
                name = spendingNameEditText.text.toString(),
                amount = spendingAmountEditText.text.toString().toFloat(),
                measure = spendingMeasureSpinner.selectedItem.toString(),
                cost = spendingCountEditText.text.toString().toFloat(),
            )
            dismiss()
        }
    }
}