package com.mrkurilin.aethalides.presentation.dialogs.entry_event_dialog

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.AethalidesDatePickerDialog
import com.mrkurilin.aethalides.data.util.AethalidesTimePickerDialog

class EntryEventDialogFragment : DialogFragment(R.layout.dialog_entry_event) {

    private val viewModel by viewModels<EntryEventViewModel>()
    private val args by navArgs<EntryEventDialogFragmentArgs>()

    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var eventDescriptionEditText: EditText
    private lateinit var noSpecificTimeCheckBox: CheckBox
    private lateinit var repeatSpinner: Spinner
    private lateinit var repeatForEditText: EditText
    private lateinit var repeatQuantityTextView: TextView
    private lateinit var cancelButton: Button
    private lateinit var doneButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        viewModel.checkArgsValue(args.event)

        setListeners()
    }

    private fun setListeners() {
        doneButton.setOnClickListener {
            viewModel.doneButtonPressed(eventDescriptionEditText.text.toString())
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
    }

    private fun initViews() {
        val view = requireView()
        dateTextView = view.findViewById(R.id.date_text_view)
        timeTextView = view.findViewById(R.id.time_text_view)
        eventDescriptionEditText = view.findViewById(R.id.point_description_edit_text)
        noSpecificTimeCheckBox = view.findViewById(R.id.no_specific_time_checkBox)
        repeatSpinner = view.findViewById(R.id.repeat_spinner)
        repeatForEditText = view.findViewById(R.id.how_long_repeat_edit_text)
        repeatQuantityTextView = view.findViewById(R.id.repeat_quantity_text_view)
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