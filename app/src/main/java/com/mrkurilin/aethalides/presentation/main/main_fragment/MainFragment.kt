package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.ColorOfMonthUtil
import com.mrkurilin.aethalides.data.util.LocalDateUtil
import com.mrkurilin.aethalides.data.util.Models
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.EventsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.PointsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.WeekDaysAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
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

        setAdapters()

        observeFlows()

        addListeners()
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

        val localDate = LocalDate.now()
        currentYearTextView.text = localDate.year.toString()
        updateCurrentVisibleMonth(localDate.month)
    }

    private fun addListeners() {
        addButton.setOnClickListener {
            showPopupMenu()
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), addButton, Gravity.CENTER)
        popupMenu.menuInflater.inflate(R.menu.add_to_calendar_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val modelToAdd: Models = when (menuItem.itemId) {
                R.id.add_point -> Models.Point
                R.id.add_note -> Models.Note
                R.id.add_event -> Models.Event
                R.id.add_eaten_food -> Models.EatenFood
                R.id.add_spending -> Models.Spending
                else -> {
                    throw IllegalArgumentException()
                }
            }
            viewModel.addItemPressed(modelToAdd)
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun updateCurrentVisibleMonth(month: Month) {
        currentMonthTextView.text = month.getDisplayName(
            TextStyle.FULL, Locale.getDefault()
        )
        currentMonthTextView.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                ColorOfMonthUtil.getColorId(month = month)
            )
        )
    }

    private fun setAdapters() {
        calendarDaysRecyclerView.adapter = WeekDaysAdapter(
            onVisibleMonthChanged = { month ->
                updateCurrentVisibleMonth(month)
            },
            onVisibleYearChanged = { year ->
                currentYearTextView.text = year
            },
            onDaySelected = { epochDay ->
                viewModel.epochDaySelected(epochDay)
            }
        )
        calendarDaysRecyclerView.scrollToPosition((Int.MAX_VALUE / 2) - 3)
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

    private fun observeFlows() {
        lifecycleScope.launch {
            launch {
                viewModel.currentShownDayFlow.collect { localDate ->
                    currentDateTextView.text = LocalDateUtil.localDateToString(localDate)
                }
            }

            launch {
                viewModel.pointsFlow.collect {
                    pointsAdapter.setItems(it)
                }
            }

            launch {
                viewModel.eventsFlow.collect {
                    eventsAdapter.setItems(it)
                }
            }

            launch {
                viewModel.caloriesCountFlow.collect { amount ->
                    currentDateCaloriesCountTextView.text = getString(R.string.kcal_count, amount)
                }
            }

            launch {
                viewModel.spendingFlow.collect { amount ->
                    currentDateSpendingTextView.text = amount.toString()
                }
            }
        }
    }
}