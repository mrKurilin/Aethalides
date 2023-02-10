package com.mrkurilin.aethalides.presentation.dialogs.entry_eaten_food_dialog

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
import com.mrkurilin.aethalides.data.model.EatenFood
import com.mrkurilin.aethalides.data.util.*
import kotlinx.coroutines.launch

class EntryEatenFoodDialogFragment : DialogFragment(R.layout.dialog_entry_eaten_food) {

    private val viewModel by viewModels<EntryEatenFoodViewModel>()

    private lateinit var headerTextView: TextView
    private lateinit var eatenFoodNameEditText: EditText
    private lateinit var eatenFoodAmountEditText: EditText
    private lateinit var eatenFoodAmountSpinner: Spinner
    private lateinit var eatenFoodKcalCountEditText: EditText
    private lateinit var datePickerTextView: TextView
    private lateinit var timePickerTextView: TextView
    private lateinit var cancelButton: Button
    private lateinit var doneButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        setListeners()

        observeFlows()
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            viewModel.dateFlow.collect { localDate ->
                datePickerTextView.text = LocalDateUtil.localDateToString(localDate)
            }
        }

        lifecycleScope.launch {
            viewModel.timeFlow.collect { localTime ->
                timePickerTextView.text = LocalTimeUtil.toString(localTime)
            }
        }
    }

    private fun initViews() {
        val view = requireView()
        headerTextView = view.findViewById(R.id.header_text_view)
        eatenFoodNameEditText = view.findViewById(R.id.spending_name_edit_text)
        eatenFoodAmountEditText = view.findViewById(R.id.amount_edit_text)
        eatenFoodAmountSpinner = view.findViewById(R.id.amount_spinner)
        eatenFoodKcalCountEditText = view.findViewById(R.id.count_edit_text_view)
        datePickerTextView = view.findViewById(R.id.date_picker_text_view)
        timePickerTextView = view.findViewById(R.id.time_picker_text_view)
        cancelButton = view.findViewById(R.id.cancel_button)
        doneButton = view.findViewById(R.id.done_button)
    }

    private fun setListeners() {
        datePickerTextView.setOnClickListener {
            showDatePickerDialog()
        }

        timePickerTextView.setOnClickListener {
            showTimePickerDialog()
        }

        doneButton.setOnClickListener {
            viewModel.addEatenFood(
                EatenFood(
                    name = eatenFoodNameEditText.text.toString(),
                    kCalCount = eatenFoodKcalCountEditText.text.toString().toInt(),
                    eatenFoodMeasure = eatenFoodAmountSpinner.selectedItem.toString(),
                    eatenFoodCount = eatenFoodAmountEditText.text.toString().toInt(),
                    utcEpochSecond = EpochSecondsUtil.fromTimeTextViewAndLocalDate(
                        timePickerTextView,
                        LocalDateUtil.localDateFromTextView(datePickerTextView),
                    ),
                    epochDay = EpochDayUtil.getEpochDayFromDateTextView(datePickerTextView),
                )
            )
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun showTimePickerDialog() {
        AethalidesTimePickerDialog.show(
            context = requireContext(),
            localTime = viewModel.timeFlow.value,
            onTimeSet = { setLocalTime ->
                viewModel.timeFlow.value = setLocalTime
            }
        )
    }

    private fun showDatePickerDialog() {
        AethalidesDatePickerDialog.show(
            context = requireContext(),
            localDate = viewModel.dateFlow.value,
            onDateSet = { setLocalDate ->
                viewModel.dateFlow.value = setLocalDate
            }
        )
    }
}