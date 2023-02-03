package com.mrkurilin.aethalides.presentation.dialogs.entry_point_dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.LocalDateUtil
import com.mrkurilin.aethalides.data.util.LocalTimeUtil
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class EntryPointDialogFragment : DialogFragment(R.layout.dialog_entry_point) {

    private val viewModel by viewModels<EntryPointViewModel>()

    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var pointDescriptionEditText: EditText
    private lateinit var noSpecificTimeCheckBox: CheckBox
    private lateinit var repeatSpinner: Spinner
    private lateinit var repeatForEditText: EditText
    private lateinit var repeatQuantityTextView: TextView
    private lateinit var cancelButton: Button
    private lateinit var doneButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)

        doneButton.setOnClickListener {
            viewModel.doneButtonPressed(
                pointDescriptionEditText.text.toString(),
                LocalDateUtil.fromTextView(dateTextView),
                LocalTimeUtil.fromTextView(timeTextView),
            )
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        dateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        timeTextView.setOnClickListener {
            showTimePickerDialog()
        }

        noSpecificTimeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            timeTextView.isEnabled = !isChecked
        }

        repeatSpinner.onItemSelectedListener = RepeatSpinnerItemSelectedListener {
            handleRepeatSpinnerSelectedItemString(repeatSpinner.selectedItem.toString())
        }

        observeData()
    }

    private fun handleRepeatSpinnerSelectedItemString(selectedItem: String) {
        when (selectedItem) {
            resources.getString(R.string.once) -> {
                // TODO:
            }
            resources.getString(R.string.daily) -> {
                // TODO:
            }
            resources.getString(R.string.weekly) -> {
                // TODO:
            }
            resources.getString(R.string.monthly) -> {
                // TODO:
            }
            resources.getString(R.string.annually) -> {
                // TODO:
            }
            resources.getString(R.string.special) -> {
                // TODO:
            }
        }
    }

    private fun showTimePickerDialog() {
        val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.currentLocalTime.value = LocalTime.of(hourOfDay, minute)
        }
        val dialog = TimePickerDialog(
            requireContext(),
            R.style.MySpinnerTimePickerStyle,
            onTimeSetListener,
            viewModel.currentLocalTime.value.hour,
            viewModel.currentLocalTime.value.minute,
            true
        )
        dialog.show()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.currentLocalDate.collect { localDate ->
                dateTextView.text = localDate.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                )
            }
        }

        lifecycleScope.launch {
            viewModel.currentLocalTime.collect { localTime ->
                timeTextView.text = localTime.format(
                    DateTimeFormatter.ofPattern("HH:mm")
                )
            }
        }
    }

    private fun showDatePickerDialog() {
        val onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.currentLocalDate.value = LocalDate.of(year, month + 1, dayOfMonth)
        }
        val dialog = DatePickerDialog(
            requireContext(),
            R.style.MySpinnerDatePickerStyle,
            onDateSetListener,
            viewModel.currentLocalDate.value.year,
            viewModel.currentLocalDate.value.monthValue - 1,
            viewModel.currentLocalDate.value.dayOfMonth
        )
        dialog.show()
    }

    private fun initViews(view: View) {
        dateTextView = view.findViewById(R.id.date_text_view)
        timeTextView = view.findViewById(R.id.time_text_view)
        pointDescriptionEditText = view.findViewById(R.id.point_description_edit_text)
        noSpecificTimeCheckBox = view.findViewById(R.id.no_specific_time_checkBox)
        repeatSpinner = view.findViewById(R.id.repeat_spinner)
        repeatForEditText = view.findViewById(R.id.how_long_repeat_edit_text)
        repeatQuantityTextView = view.findViewById(R.id.repeat_quantity_text_view)
        cancelButton = view.findViewById(R.id.cancel_button)
        doneButton = view.findViewById(R.id.done_button)
    }
}