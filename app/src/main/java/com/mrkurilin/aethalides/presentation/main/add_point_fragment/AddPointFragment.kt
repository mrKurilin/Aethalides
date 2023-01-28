package com.mrkurilin.aethalides.presentation.main.add_point_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mrkurilin.aethalides.R

class AddPointFragment : Fragment(R.layout.fragment_add_point) {

    private val viewModel by viewModels<AddPointViewModel>()
}