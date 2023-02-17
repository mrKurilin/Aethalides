package com.mrkurilin.aethalides.presentation.dialogs.entry_event_dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.*
import kotlinx.coroutines.launch

class EntryEventDialogFragment : EntryItemDialogFragment(R.layout.dialog_entry_event) {

    private val viewModel by viewModels<EntryEventViewModel>()
    private val args by navArgs<EntryEventDialogFragmentArgs>()

    private lateinit var datePickerTextView: TextView
    private lateinit var timePickerTextView: TextView
    private lateinit var eventNameEditText: EditText
    private lateinit var noSpecificTimeCheckBox: CheckBox
    private lateinit var isAnnuallyCheckBox: CheckBox
    private lateinit var cancelButton: Button
    private lateinit var doneButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        viewModel.checkArgsValue(args.event)

        if (args.event != null) {
            val event = args.event!!
            eventNameEditText.setText(event.name)
            datePickerTextView.text = EpochDayUtil.epochDayToDateString(event.epochDay)
            timePickerTextView.text = event.timeText
            noSpecificTimeCheckBox.isChecked = (event.timeText == "")
            isAnnuallyCheckBox.isChecked = event.isAnnually
        }

        observeFlows()

        setListeners()
    }

    private fun observeFlows() {

        lifecycleScope.launch {
            viewModel.currentLocalDateFlow.collect { localDate ->
                datePickerTextView.text = LocalDateUtil.localDateToString(localDate)
            }
        }

        lifecycleScope.launch {
            viewModel.currentLocalTimeFlow.collect { localTime ->
                if (!noSpecificTimeCheckBox.isChecked) {
                    timePickerTextView.text = LocalTimeUtil.toString(localTime)
                }
            }
        }
    }

    private fun setListeners() {
        doneButton.setOnClickListener {
            viewModel.doneButtonPressed(
                eventNameEditText.text.toString(),
                datePickerTextView.text.toString(),
                timePickerTextView.text.toString(),
                isAnnuallyCheckBox.isChecked,
            )
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        datePickerTextView.setOnClickListener {
            showDatePickerDialog()
        }

        timePickerTextView.setOnClickListener {
            showTimePickerDialog()
        }

        noSpecificTimeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            timePickerTextView.isEnabled = !isChecked
            if (isChecked) {
                timePickerTextView.text = ""
            } else {
                timePickerTextView.text = LocalTimeUtil.toString(
                    viewModel.currentLocalTimeFlow.value
                )
            }
        }
    }

    private fun initViews() {
        val view = requireView()
        datePickerTextView = view.findViewById(R.id.date_picker_text_view)
        timePickerTextView = view.findViewById(R.id.time_picker_text_view)
        eventNameEditText = view.findViewById(R.id.event_name_edit_text)
        noSpecificTimeCheckBox = view.findViewById(R.id.no_specific_time_checkBox)
        isAnnuallyCheckBox = view.findViewById(R.id.is_annually_checkBox)
        cancelButton = view.findViewById(R.id.cancel_button)
        doneButton = view.findViewById(R.id.done_button)
    }

    private fun showTimePickerDialog() {
        AethalidesTimePickerDialog.show(
            context = requireContext(),
            localTime = viewModel.currentLocalTimeFlow.value,
            onTimeSet = { localTime ->
                viewModel.currentLocalTimeFlow.value = localTime
            }
        )
    }

    private fun showDatePickerDialog() {
        AethalidesDatePickerDialog.show(
            context = requireContext(),
            localDate = viewModel.currentLocalDateFlow.value,
            onDateSet = { localDate ->
                viewModel.currentLocalDateFlow.value = localDate
            }
        )
    }
}