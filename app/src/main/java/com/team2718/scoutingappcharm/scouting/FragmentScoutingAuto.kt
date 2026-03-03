package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import com.team2718.scoutingappcharm.custom.CounterView

class FragmentScoutingAuto : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    private lateinit var fuelScoredCounter: CounterView
    private lateinit var fuelMissedCounter: CounterView

    private lateinit var movedCheckbox: CheckBox
    private lateinit var climbCheckbox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_auto, container, false)

        fuelScoredCounter = view.findViewById(R.id.counterScored)
        fuelMissedCounter = view.findViewById(R.id.counterMissed)

        movedCheckbox = view.findViewById(R.id.movedCheckbox)
        climbCheckbox = view.findViewById(R.id.climbedCheckbox)

        populateView()

        view.findViewById<Button>(R.id.autoNext).setOnClickListener { next() }

        if (viewModel.doPageSkipping && viewModel.currentReport.stagesComplete >= 2) {
            findNavController().navigate(R.id.nav_scouting_teleop)
        } else {
            viewModel.doPageSkipping = false
        }

        return view
    }

    private fun populateView() {
        fuelScoredCounter.setValue(viewModel.currentReport.autoFuel)
        fuelMissedCounter.setValue(viewModel.currentReport.autoFuelMissed)

        movedCheckbox.isChecked = viewModel.currentReport.autoClimbed == true
        movedCheckbox.isChecked = viewModel.currentReport.didLeave == true
    }

    private fun writeToViewModel() {
        viewModel.currentReport.autoFuel = fuelScoredCounter.getValue()
        viewModel.currentReport.autoFuelMissed = fuelMissedCounter.getValue()

        viewModel.currentReport.autoClimbed = climbCheckbox.isChecked
        viewModel.currentReport.didLeave = movedCheckbox.isChecked
    }

    private fun next() {
        writeToViewModel()

        // Increment stages complete
        if (viewModel.currentReport.stagesComplete == 1) {
            viewModel.currentReport.stagesComplete = 2
        }

        viewModel.updateDB()

        findNavController().navigate(R.id.nav_scouting_teleop)
    }

    override fun onPause() {
        super.onPause()

        writeToViewModel()

        viewModel.updateDB()
    }
}