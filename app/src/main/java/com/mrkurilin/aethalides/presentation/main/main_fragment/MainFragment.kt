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
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventsAdapter: EventsRecyclerViewAdapter
    private lateinit var pointsAdapter: PointsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setAdapters()

        observeFlows()

        addListeners()
    }

    private fun initViews() {
        val localDate = LocalDate.now()
        binding.currentYearTextView.text = localDate.year.toString()
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
            viewModel.addItemPressed(modelToAdd)
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun updateCurrentVisibleMonth(month: Month) {
        binding.currentMonthTextView.text = month.name
        binding.currentMonthTextView.setTextColor(
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
                binding.currentYearTextView.text = year
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
                viewModel.editEvent(event)
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
                viewModel.editPoint(point)
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

    private fun observeFlows() {
        lifecycleScope.launch {
            launch {
                viewModel.currentShownDayFlow.collect { localDate ->
                    binding.currentDayTextView.text = LocalDateUtil.localDateToString(localDate)
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
                    binding.kcalTextView.text = getString(R.string.kcal_count, amount)
                }
            }

            launch {
                viewModel.spendingFlow.collect { amount ->
                    binding.spendingTextView.text = amount.toString()
                }
            }
        }
    }
}