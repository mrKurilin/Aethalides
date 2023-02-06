package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.MainDaysAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var viewPager2: ViewPager2
    private lateinit var mainDaysAdapter: MainDaysAdapter
    private lateinit var addButton: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setAdapter()

        addButton.setOnClickListener {
            showPopupMenu()
        }

            observeFlows()
    }

    private fun showPopupMenu() {
        val diff = viewPager2.currentItem
        val currentShownEpochDay = LocalDate.now().toEpochDay() + diff
        val currentEpochSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

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
                    // TODO: show dialog
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

    private fun observeFlows() {
        lifecycleScope.launch{
            viewModel.daysToEpochDaysMapFlow.collect { map ->
                mainDaysAdapter.setItems(map)
            }
        }
    }

    private fun initViews() {
        val view = requireView()
        viewPager2 = view.findViewById(R.id.main_recycler_view)
        addButton = view.findViewById(R.id.add_button)
    }

    private fun setAdapter() {
        mainDaysAdapter = MainDaysAdapter(
            deletePoint = { point ->
                viewModel.deletePoint(point)
            },
            updatePoint = { point ->
                viewModel.editPoint(point)
            },
            deleteEvent = { event ->
                viewModel.deleteEvent(event)
            },
            updateEvent = { event ->
                viewModel.editEvent(event)
            }
        )
        viewPager2.offscreenPageLimit = 10
        viewPager2.adapter = mainDaysAdapter
        viewPager2.setCurrentItem(Int.MAX_VALUE / 2, false)
    }
}