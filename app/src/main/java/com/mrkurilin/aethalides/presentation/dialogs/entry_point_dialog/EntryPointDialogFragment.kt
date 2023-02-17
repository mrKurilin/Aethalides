package com.mrkurilin.aethalides.presentation.dialogs.entry_point_dialog

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.*
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class EntryPointDialogFragment : EntryItemDialogFragment(R.layout.dialog_entry_point) {

    private val viewModel by viewModels<EntryPointViewModel>()
    private val args by navArgs<EntryPointDialogFragmentArgs>()

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
        initViews()

        viewModel.checkArgsValue(args.point)

        if (args.point != null) {
            updateUi(args.point!!)
        }

        setListeners()

        observeData()
    }

    private fun updateUi(point: Point) {

    }

    private fun setListeners() {
        doneButton.setOnClickListener {
            viewModel.doneButtonPressed(
                pointDescriptionEditText.text.toString(),
                LocalDateUtil.localDateFromTextView(dateTextView),
                LocalTimeUtil.localTimeFromTextView(timeTextView),
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

        repeatSpinner.onItemSelectedListener = AethalidesOnItemSelectedListener { _, _, _, _ ->
            handleRepeatSpinnerSelectedItemString(repeatSpinner.selectedItem.toString())
        }
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
        AethalidesTimePickerDialog.show(
            context = requireContext(),
            localTime = viewModel.currentLocalTime.value,
            onTimeSet = { localTime ->
                viewModel.currentLocalTime.value = localTime
            }
        )
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
        AethalidesDatePickerDialog.show(
            context = requireContext(),
            localDate = viewModel.currentLocalDate.value,
            onDateSet = { localDate ->
                viewModel.currentLocalDate.value = localDate
            }
        )
    }

    private fun initViews() {
        val view = requireView()
        dateTextView = view.findViewById(R.id.date_picker_text_view)
        timeTextView = view.findViewById(R.id.time_picker_text_view)
        pointDescriptionEditText = view.findViewById(R.id.event_name_edit_text)
        noSpecificTimeCheckBox = view.findViewById(R.id.no_specific_time_checkBox)
        repeatSpinner = view.findViewById(R.id.repeat_spinner)
        repeatForEditText = view.findViewById(R.id.how_long_repeat_edit_text)
        repeatQuantityTextView = view.findViewById(R.id.repeat_quantity_text_view)
        cancelButton = view.findViewById(R.id.cancel_button)
        doneButton = view.findViewById(R.id.done_button)
    }
}