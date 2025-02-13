package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import com.team2718.scoutingappcharm.custom.CounterView

class FragmentScoutingAuto : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    private lateinit var counterL1: CounterView
    private lateinit var counterL2: CounterView
    private lateinit var counterL3: CounterView
    private lateinit var counterL4: CounterView

    private lateinit var counterProcessed: CounterView
    private lateinit var counterBarged: CounterView
    private lateinit var movedCheckbox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_auto, container, false)

        counterL1 = view.findViewById(R.id.counterL1)
        counterL2 = view.findViewById(R.id.counterL2)
        counterL3 = view.findViewById(R.id.counterL3)
        counterL4 = view.findViewById(R.id.counterL4)

        counterProcessed = view.findViewById(R.id.counterProcessed)
        counterBarged = view.findViewById(R.id.counterBarged)

        movedCheckbox = view.findViewById(R.id.movedCheckbox)

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
        counterL1.setValue(viewModel.currentReport.autoL1)
        counterL2.setValue(viewModel.currentReport.autoL2)
        counterL3.setValue(viewModel.currentReport.autoL3)
        counterL4.setValue(viewModel.currentReport.autoL4)

        counterProcessed.setValue(viewModel.currentReport.autoNumProcessed)
        counterBarged.setValue(viewModel.currentReport.autoNumNetFromRobot)

        movedCheckbox.isChecked = viewModel.currentReport.didLeave == true
    }

    private fun writeToViewModel() {
        viewModel.currentReport.autoL1 = counterL1.getValue()
        viewModel.currentReport.autoL2 = counterL2.getValue()
        viewModel.currentReport.autoL3 = counterL3.getValue()
        viewModel.currentReport.autoL4 = counterL4.getValue()

        viewModel.currentReport.autoNumProcessed = counterProcessed.getValue()
        viewModel.currentReport.autoNumNetFromRobot = counterBarged.getValue()

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