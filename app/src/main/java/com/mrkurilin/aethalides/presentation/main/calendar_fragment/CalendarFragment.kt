package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mrkurilin.aethalides.R
import kotlinx.coroutines.launch
import java.time.Month

class CalendarFragment : Fragment(R.layout.fragment_calendar),
    VisibleMonthListener, VisibleYearListener {

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
        recyclerView.setHasFixedSize(true)
        val adapter = DaysAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)

        observePoints(adapter)
    }

    private fun observePoints(adapter: DaysAdapter) {
        lifecycleScope.launch {
            viewModel.observePointsColors { points ->
                adapter.setItems(points)
            }
        }
    }

    override fun onVisibleMonthChanged(month: Month) {
        monthTextView.text = month.toString()
    }

    override fun onVisibleYearListener(year: Int) {
        yearTextView.text = year.toString()
    }
}