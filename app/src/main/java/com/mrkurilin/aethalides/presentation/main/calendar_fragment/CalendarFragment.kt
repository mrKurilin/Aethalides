package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import java.time.Month

class CalendarFragment : Fragment(R.layout.fragment_calendar), VisibleMonthListener, VisibleYearListener {

    private val viewModel by viewModels<CalendarViewModel>()

    private lateinit var recyclerView: GridCalendarRecyclerView
    private lateinit var monthTextView: TextView
    private lateinit var yearTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        val repo = (requireActivity().application as AethalidesApp).providePointsRepository()

        recyclerView = view.findViewById(R.id.recycler_calendar)
        recyclerView.setHasFixedSize(true)
        val adapter = DaysAdapter(repo, this, this)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(Int.MAX_VALUE/2)
    }

    private fun initViews(){
        val view = requireView()
        monthTextView = view.findViewById(R.id.month_text_view)
        yearTextView = view.findViewById(R.id.year_text_view)
    }

    override fun onVisibleMonthChanged(month: Month) {
        monthTextView.text = month.toString()
    }

    override fun onVisibleYearListener(year: Int) {
        yearTextView.text = year.toString()
    }
}