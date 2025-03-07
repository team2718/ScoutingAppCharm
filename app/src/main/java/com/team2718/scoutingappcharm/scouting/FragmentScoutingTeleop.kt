package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import com.team2718.scoutingappcharm.custom.CounterView

class FragmentScoutingTeleop : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    private lateinit var counterL1: CounterView
    private lateinit var counterL2: CounterView
    private lateinit var counterL3: CounterView
    private lateinit var counterL4: CounterView

    private lateinit var counterProcessed: CounterView
    private lateinit var counterBarged: CounterView
    private lateinit var counterHuman: CounterView
    private lateinit var counterMissed: CounterView

    private lateinit var hangSpinner: Spinner
    private lateinit var defenseCheckbox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_teleop, container, false)

        counterL1 = view.findViewById(R.id.counterL1)
        counterL2 = view.findViewById(R.id.counterL2)
        counterL3 = view.findViewById(R.id.counterL3)
        counterL4 = view.findViewById(R.id.counterL4)

        counterProcessed = view.findViewById(R.id.counterProcessed)
        counterBarged = view.findViewById(R.id.counterBarged)
        defenseCheckbox = view.findViewById(R.id.checkBox)

        hangSpinner = view.findViewById(R.id.spinner)

        populateView()

        view.findViewById<Button>(R.id.teleopNext).setOnClickListener { next() }

        if (viewModel.doPageSkipping && viewModel.currentReport.stagesComplete >= 3) {
            findNavController().navigate(R.id.nav_scouting_post_match)
        } else {
            viewModel.doPageSkipping = false
        }

        return view
    }

    private fun populateView() {
        counterL1.setValue(viewModel.currentReport.teleopL1)
        counterL2.setValue(viewModel.currentReport.teleopL2)
        counterL3.setValue(viewModel.currentReport.teleopL3)
        counterL4.setValue(viewModel.currentReport.teleopL4)

        counterProcessed.setValue(viewModel.currentReport.teleopNumProcessed)
        counterBarged.setValue(viewModel.currentReport.teleopNumNetFromRobot)

        hangSpinner.setSelection(viewModel.currentReport.hangType)
        defenseCheckbox.isChecked = viewModel.currentReport.playedDefense == true
    }

    private fun writeToViewModel() {
        viewModel.currentReport.teleopL1 = counterL1.getValue()
        viewModel.currentReport.teleopL2 = counterL2.getValue()
        viewModel.currentReport.teleopL3 = counterL3.getValue()
        viewModel.currentReport.teleopL4 = counterL4.getValue()

        viewModel.currentReport.teleopNumProcessed = counterProcessed.getValue()
        viewModel.currentReport.teleopNumNetFromRobot = counterBarged.getValue()

        viewModel.currentReport.hangType = hangSpinner.selectedItemPosition
        viewModel.currentReport.playedDefense = defenseCheckbox.isChecked
    }

    private fun next() {
        writeToViewModel()

        // Increment stages complete
        if (viewModel.currentReport.stagesComplete == 2) {
            viewModel.currentReport.stagesComplete = 3
        }

        viewModel.updateDB(true)

        findNavController().navigate(R.id.nav_scouting_post_match)
    }

    override fun onPause() {
        super.onPause()

        writeToViewModel()

        viewModel.updateDB()
    }
}