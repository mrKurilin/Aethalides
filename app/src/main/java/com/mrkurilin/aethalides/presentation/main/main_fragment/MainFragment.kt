package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.EventsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.PointsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.WeekDaysAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var currentYearTextView: TextView
    private lateinit var currentMonthTextView: TextView
    private lateinit var currentDateTextView: TextView
    private lateinit var currentDateSpendingTextView: TextView
    private lateinit var currentDateCaloriesCountTextView: TextView
    private lateinit var calendarDaysRecyclerView: RecyclerView
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var pointsRecyclerView: RecyclerView
    private lateinit var addButton: ImageButton

    private lateinit var eventsAdapter: EventsRecyclerViewAdapter
    private lateinit var pointsAdapter: PointsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        currentYearTextView.text = LocalDate.now().year.toString()
        currentMonthTextView.text = LocalDate.now().month.getDisplayName(
            TextStyle.FULL, Locale.getDefault()
        )

        setAdapters()

        addButton.setOnClickListener {
            showPopupMenu()
        }

        lifecycleScope.launch {
            viewModel.pointsFlow.collect {
                pointsAdapter.setItems(it)
            }
        }

        lifecycleScope.launch {
            viewModel.eventsFlow.collect {
                eventsAdapter.setItems(it)
            }
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), addButton, Gravity.CENTER)
        popupMenu.menuInflater.inflate(R.menu.add_to_calendar_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_point -> {
                    viewModel.addPointPressed()
                    return@setOnMenuItemClickListener true
                }
                R.id.add_note -> {
                    viewModel.addNotePressed()
                    return@setOnMenuItemClickListener true
                }
                R.id.add_event -> {
                    viewModel.addEventPressed()
                    return@setOnMenuItemClickListener true
                }
                R.id.add_eaten_food -> {
                    viewModel.addEatenFoodPressed()
                    return@setOnMenuItemClickListener true
                }
                R.id.add_spending -> {
                    viewModel.addSpendingPressed()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    throw IllegalStateException()
                }
            }
        }
        popupMenu.show()
    }

    private fun initViews() {
        val view = requireView()
        addButton = view.findViewById(R.id.add_button)
        currentYearTextView = view.findViewById(R.id.current_year_text_view)
        currentMonthTextView = view.findViewById(R.id.current_month_text_view)
        currentDateTextView = view.findViewById(R.id.current_day_text_view)
        currentDateSpendingTextView = view.findViewById(R.id.spending_text_view)
        currentDateCaloriesCountTextView = view.findViewById(R.id.kcal_text_view)
        calendarDaysRecyclerView = view.findViewById(R.id.calendar_days_recycler_view)
        eventsRecyclerView = view.findViewById(R.id.events_recycler_view)
        pointsRecyclerView = view.findViewById(R.id.points_recycler_view)
    }

    private fun setAdapters() {
        calendarDaysRecyclerView.adapter = WeekDaysAdapter(
            onVisibleMonthChanged = { month ->
                currentMonthTextView.text = month
            }, onVisibleYearChanged = { year ->
                currentYearTextView.text = year
            }
        )
        calendarDaysRecyclerView.scrollToPosition(Int.MAX_VALUE / 2)
        LinearSnapHelper().attachToRecyclerView(calendarDaysRecyclerView)

        eventsAdapter = EventsRecyclerViewAdapter(
            deleteEvent = { event ->
                viewModel.deleteEvent(event)
            },
            editEvent = { event ->
                viewModel.editEvent(event)
            }
        )
        eventsRecyclerView.adapter = eventsAdapter

        pointsAdapter = PointsRecyclerViewAdapter(
            deletePoint = { point ->
                viewModel.deletePoint(point)
            },
            editPoint = { point ->
                viewModel.editPoint(point)
            }
        )
        pointsRecyclerView.adapter = pointsAdapter
    }
}