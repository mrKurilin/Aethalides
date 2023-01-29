package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.MainDaysAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var viewPager: ViewPager2
    private lateinit var mainDaysAdapter: MainDaysAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setAdapter()

        observeFlows()
    }

    private fun observeFlows() {

    }

    private fun initViews() {
        viewPager = requireView().findViewById(R.id.main_view_pager)
    }

    private fun setAdapter() {
        mainDaysAdapter = MainDaysAdapter(
            deletePoint = {

            },
            editPoint = {
            }
        )
        viewPager.adapter = mainDaysAdapter
        viewPager.setCurrentItem(Int.MAX_VALUE / 2, false)
    }
}