package com.mrkurilin.aethalides.presentation.main.to_do_list_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.mrkurilin.aethalides.R
import kotlinx.coroutines.launch

class ToDoListFragment : Fragment() {

    private val viewModel by viewModels<ToDoListViewModel>()

    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initRecyclerView()
    }

    private fun initViews() {
        val view = requireView()
        viewPager = view.findViewById(R.id.main_view_pager)
    }

    private fun initRecyclerView() {
        val adapter = ToDoDaysAdapter(
            onPointUpdated = { point ->
                val oldValue = point.isDone
                val newPoint = point.copy(isDone = !oldValue)
                viewModel.updatePoint(newPoint)
            }
        )
        viewPager.adapter = adapter
        viewPager.setCurrentItem(Int.MAX_VALUE / 2, false)

        lifecycleScope.launch {
            viewModel.epochDaysToPointsFlow.collect { epochDaysToPointsMap ->
                adapter.setItems(epochDaysToPointsMap)
            }
        }
    }
}