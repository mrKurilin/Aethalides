package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.views.GridCalendarRecyclerView
import kotlinx.coroutines.launch

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private lateinit var recyclerView: GridCalendarRecyclerView
    private lateinit var monthTextView: TextView
    private lateinit var yearTextView: TextView

    private val viewModel by viewModels<CalendarViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initRecyclerView()
    }

    private fun initViews() {
        val view = requireView()
        monthTextView = view.findViewById(R.id.month_text_view)
        yearTextView = view.findViewById(R.id.year_text_view)
        recyclerView = view.findViewById(R.id.recycler_calendar)
    }

    private fun initRecyclerView() {
        val adapter = CalendarDaysAdapter(
            onVisibleMonthChanged = { month ->
                monthTextView.text = month
            },
            onVisibleYearChanged = { year ->
                yearTextView.text = year
            }
        )

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)

        lifecycleScope.launch {
            viewModel.pointsColorsOfEpochDaysMapFlow.collect { map ->
                adapter.setItems(map)
            }
        }
    }
}