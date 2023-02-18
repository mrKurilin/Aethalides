package com.mrkurilin.aethalides.presentation.dialogs.entry_point_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.*
import com.mrkurilin.aethalides.data.util.NavigationConstants.Companion.EPOCH_DAY_KEY
import com.mrkurilin.aethalides.databinding.DialogEntryPointBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate

class EntryPointDialogFragment : EntryItemDialogFragment<DialogEntryPointBinding>(
    R.layout.dialog_entry_point
) {

    private val viewModel by viewModels<EntryPointViewModel>()
    private val args by navArgs<EntryPointDialogFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.checkArgsValue(args.point)

        val epochDay = arguments?.getLong(EPOCH_DAY_KEY) ?: LocalDate.now().toEpochDay()
        viewModel.currentLocalDate.value = LocalDate.ofEpochDay(epochDay)

        if (args.point != null) {
            updateUi(args.point!!)
        }

        setListeners()

        observeFlows()
    }

    private fun updateUi(point: Point) {

    }

    private fun setListeners() {
        binding.doneButton.setOnClickListener {
            viewModel.doneButtonPressed(
                binding.pointNameEditText.text.toString(),
                LocalDateUtil.localDateFromTextView(binding.datePickerTextView),
                LocalTimeUtil.localTimeFromTextView(binding.timePickerTextView),
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
        }

        binding.repeatSpinner.onItemSelectedListener =
            AethalidesOnItemSelectedListener { _, _, _, _ ->
                handleRepeatSpinnerSelectedItemString(binding.repeatSpinner.selectedItem.toString())
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

    private fun observeFlows() = lifecycleScope.launch {
        combine(
            viewModel.currentLocalDate,
            viewModel.currentLocalTime
        ) { localDate, localTime ->
            binding.datePickerTextView.text = LocalDateUtil.localDateToString(localDate)
            binding.timePickerTextView.text = LocalTimeUtil.toString(localTime)
        }.collect()
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

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEntryPointBinding {
        return DialogEntryPointBinding.inflate(inflater, container, false)
    }
}