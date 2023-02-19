package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.ColorOfMonthUtil
import com.mrkurilin.aethalides.data.util.LocalDateUtil
import com.mrkurilin.aethalides.data.util.Models
import com.mrkurilin.aethalides.data.util.RecyclerViewAdapterDataObserver
import com.mrkurilin.aethalides.databinding.FragmentMainBinding
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.EventsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.PointsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.WeekDaysAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController
        get() = findNavController()

    private lateinit var eventsAdapter: EventsRecyclerViewAdapter
    private lateinit var pointsAdapter: PointsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setAdapters()

        addListeners()

        lifecycleScope.launch {
            combine(
                viewModel.currentShownDayFlow,
                viewModel.pointsFlow,
                viewModel.eventsFlow,
                viewModel.spendingFlow,
                viewModel.caloriesCountFlow,
            ) { localDate, points, events, spending, caloriesCount ->
                binding.currentDayTextView.text = LocalDateUtil.localDateToString(localDate)
                pointsAdapter.setItems(points)
                eventsAdapter.setItems(events)
                binding.kcalTextView.text = getString(R.string.kcal_count, caloriesCount)
                binding.spendingTextView.text = spending.toString()
            }.collect()
        }
    }

    private fun initViews() {
        val localDate = LocalDate.now()
        binding.currentCalendarYearTextView.text = localDate.year.toString()
        updateCurrentVisibleMonth(localDate.month)
    }

    private fun addListeners() {
        binding.addButton.setOnClickListener {
            showPopupMenu()
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.addButton, Gravity.CENTER)
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
            viewModel.addItemPressed(modelToAdd, navController)
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun updateCurrentVisibleMonth(month: Month) {
        binding.currentCalendarMonthTextView.text = month.name
        binding.currentCalendarMonthTextView.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                ColorOfMonthUtil.getColorId(month = month)
            )
        )
    }

    private fun setAdapters() {
        binding.calendarDaysRecyclerView.adapter = WeekDaysAdapter(
            onVisibleMonthChanged = { month ->
                updateCurrentVisibleMonth(month)
            },
            onVisibleYearChanged = { year ->
                binding.currentCalendarYearTextView.text = year
            },
            onDaySelected = { epochDay ->
                viewModel.epochDaySelected(epochDay)
            }
        )
        binding.calendarDaysRecyclerView.scrollToPosition((Int.MAX_VALUE / 2) - 3)
        LinearSnapHelper().attachToRecyclerView(binding.calendarDaysRecyclerView)

        eventsAdapter = EventsRecyclerViewAdapter(
            deleteEvent = { event ->
                viewModel.deleteEvent(event)
            },
            editEvent = { event ->
                viewModel.editEvent(event, navController)
            }
        )

        eventsAdapter.registerAdapterDataObserver(
            RecyclerViewAdapterDataObserver(
                binding.noEventsTextView,
                binding.eventsRecyclerView
            )
        )

        binding.eventsRecyclerView.adapter = eventsAdapter

        pointsAdapter = PointsRecyclerViewAdapter(
            deletePoint = { point ->
                viewModel.deletePoint(point)
            },
            editPoint = { point ->
                viewModel.editPoint(point, navController)
            },
            updatePoint = { point ->
                viewModel.updatePoint(point)
            }
        )

        pointsAdapter.registerAdapterDataObserver(
            RecyclerViewAdapterDataObserver(
                binding.noPointsTextView,
                binding.pointsRecyclerView
            )
        )

        binding.pointsRecyclerView.adapter = pointsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}