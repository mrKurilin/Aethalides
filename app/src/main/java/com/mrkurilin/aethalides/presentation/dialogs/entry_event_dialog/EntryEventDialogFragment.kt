package com.mrkurilin.aethalides.presentation.dialogs.entry_event_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.*
import com.mrkurilin.aethalides.databinding.DialogEntryEventBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate

class EntryEventDialogFragment : EntryItemDialogFragment<DialogEntryEventBinding>(
    R.layout.dialog_entry_event
) {

    private val viewModel by viewModels<EntryEventViewModel>()
    private val args by navArgs<EntryEventDialogFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val epochDay = arguments?.getLong(
            NavigationConstants.EPOCH_DAY_KEY
        ) ?: LocalDate.now().toEpochDay()
        viewModel.currentLocalDateFlow.value = LocalDate.ofEpochDay(epochDay)

        viewModel.checkArgsValue(args.event)

        if (args.event != null) {
            val event = args.event!!
            binding.eventNameEditText.setText(event.name)
            binding.datePickerTextView.text = EpochDayUtil.epochDayToDateString(event.epochDay)
            binding.timePickerTextView.text = event.timeText
            binding.noSpecificTimeCheckBox.isChecked = (event.timeText == "")
            binding.isAnnuallyCheckBox.isChecked = event.isAnnually
        }

        observeFlows()

        setListeners()
    }

    private fun observeFlows() = lifecycleScope.launch {
        combine(
            viewModel.currentLocalTimeFlow,
            viewModel.currentLocalDateFlow,
        ) { localTime, localDate ->
            binding.timePickerTextView.text = LocalTimeUtil.toString(localTime)
            binding.datePickerTextView.text = LocalDateUtil.localDateToString(localDate)
        }.collect()
    }

    private fun setListeners() {
        binding.doneButton.setOnClickListener {
            viewModel.doneButtonPressed(
                binding.eventNameEditText.text.toString(),
                binding.datePickerTextView.text.toString(),
                binding.timePickerTextView.text.toString(),
                binding.isAnnuallyCheckBox.isChecked,
            )
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.datePickerTextView.setOnClickListener {
            showDatePickerDialog()
        }

        binding.timePickerTextView.setOnClickListener {
            showTimePickerDialog()
        }

        binding.noSpecificTimeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.timePickerTextView.isEnabled = !isChecked
            if (isChecked) {
                binding.timePickerTextView.text = ""
            } else {
                binding.timePickerTextView.text = LocalTimeUtil.toString(
                    viewModel.currentLocalTimeFlow.value
                )
            }
        }
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

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEntryEventBinding {
        return DialogEntryEventBinding.inflate(inflater, container, false)
    }
}