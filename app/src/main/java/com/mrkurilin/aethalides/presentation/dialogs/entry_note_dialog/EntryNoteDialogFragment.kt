package com.mrkurilin.aethalides.presentation.dialogs.entry_note_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.EntryItemDialogFragment
import com.mrkurilin.aethalides.data.util.EntryState
import com.mrkurilin.aethalides.databinding.DialogEntryNoteBinding
import kotlinx.coroutines.launch

class EntryNoteDialogFragment : EntryItemDialogFragment<DialogEntryNoteBinding>(
    R.layout.dialog_entry_note
) {

    private val viewModel by viewModels<EntryNoteViewModel>()
    private val args by navArgs<EntryNoteDialogFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.checkArgsValue(args.note)

        observeEntryState()

        setListeners()
    }

    private fun observeEntryState() {
        lifecycleScope.launch {
            viewModel.entryStateFlow.collect {
                updateUi(it)
            }
        }
    }

    private fun updateUi(entryState: EntryState) {
        when (entryState) {
            is EntryState.Add -> {
                binding.doneButton.setText(R.string.add)
            }
            is EntryState.Edit -> {
                binding.doneButton.setText(R.string.edit)
                binding.noteEditText.setText(
                    args.note?.text
                )
            }
        }
    }

    private fun setListeners() {
        binding.doneButton.setOnClickListener {
            viewModel.doneButtonPressed(binding.noteEditText.text.toString())
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): DialogEntryNoteBinding {
        return DialogEntryNoteBinding.inflate(layoutInflater, container, false)
    }
}