package com.mrkurilin.aethalides.presentation.dialogs.entry_note_dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.EntryState
import kotlinx.coroutines.launch

class EntryNoteDialogFragment : DialogFragment(R.layout.dialog_entry_note) {

    private val viewModel by viewModels<EntryNoteViewModel>()
    private val args by navArgs<EntryNoteDialogFragmentArgs>()

    private lateinit var editText: EditText
    private lateinit var doneButton: Button
    private lateinit var cancelButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

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
                doneButton.setText(R.string.add)
            }
            is EntryState.Edit -> {
                doneButton.setText(R.string.edit)
                editText.setText(
                    args.note?.text
                )
            }
        }
    }

    private fun initViews() {
        val view = requireView()
        editText = view.findViewById(R.id.note_edit_text)
        doneButton = view.findViewById(R.id.done_button)
        cancelButton = view.findViewById(R.id.cancel_button)
    }

    private fun setListeners() {
        doneButton.setOnClickListener {
            viewModel.doneButtonPressed(editText.text.toString())
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}