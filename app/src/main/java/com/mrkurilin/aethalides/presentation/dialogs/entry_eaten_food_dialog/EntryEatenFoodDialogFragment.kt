package com.mrkurilin.aethalides.presentation.dialogs.entry_eaten_food_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.EatenFood
import com.mrkurilin.aethalides.data.util.*
import com.mrkurilin.aethalides.data.util.NavigationConstants.Companion.EPOCH_DAY_KEY
import com.mrkurilin.aethalides.databinding.DialogEntryEatenFoodBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate

class EntryEatenFoodDialogFragment : EntryItemDialogFragment<DialogEntryEatenFoodBinding>(
    R.layout.dialog_entry_eaten_food
) {

    private val viewModel by viewModels<EntryEatenFoodViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()

        observeFlows()

        val epochDay = arguments?.getLong(EPOCH_DAY_KEY) ?: LocalDate.now().toEpochDay()
        viewModel.currentLocalDateFlow.value = LocalDate.ofEpochDay(epochDay)
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
        binding.datePickerTextView.setOnClickListener {
            showDatePickerDialog()
        }

        binding.timePickerTextView.setOnClickListener {
            showTimePickerDialog()
        }

        binding.doneButton.setOnClickListener {
            viewModel.addEatenFood(
                EatenFood(
                    name = binding.nameEditText.text.toString(),
                    kCalCount = binding.countTextView.text.toString().toInt(),
                    eatenFoodMeasure = binding.measureSpinner.selectedItem.toString(),
                    eatenFoodCount = binding.foodAmountEditText.text.toString().toInt(),
                    utcEpochSecond = EpochSecondsUtil.fromTimeTextViewAndLocalDate(
                        binding.timePickerTextView,
                        LocalDateUtil.localDateFromTextView(binding.datePickerTextView),
                    ),
                    epochDay = EpochDayUtil.getEpochDayFromDateTextView(binding.datePickerTextView),
                )
            )
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun showTimePickerDialog() {
        AethalidesTimePickerDialog.show(
            context = requireContext(),
            localTime = viewModel.currentLocalTimeFlow.value,
            onTimeSet = { setLocalTime ->
                viewModel.currentLocalTimeFlow.value = setLocalTime
            }
        )
    }

    private fun showDatePickerDialog() {
        AethalidesDatePickerDialog.show(
            context = requireContext(),
            localDate = viewModel.currentLocalDateFlow.value,
            onDateSet = { setLocalDate ->
                viewModel.currentLocalDateFlow.value = setLocalDate
            }
        )
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEntryEatenFoodBinding {
        return DialogEntryEatenFoodBinding.inflate(inflater, container, false)
    }
}