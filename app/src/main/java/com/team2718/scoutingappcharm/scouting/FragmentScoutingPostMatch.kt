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

class FragmentScoutingPostMatch : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    private lateinit var notes: TextView
    private lateinit var cardSpinner: Spinner

    public var teamsInfo = Array(1) { Array(1000) { kotlin.Array(5) { 0 } } }
    var submissions = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_post_match, container, false)

        notes = view.findViewById(R.id.text_notes)
        cardSpinner = view.findViewById(R.id.card_received)

        populateView()

        view.findViewById<Button>(R.id.submit).setOnClickListener { submit() }

        // This is the last page, stop skipping
        viewModel.doPageSkipping = false

        return view
    }

    private fun populateView() {
        cardSpinner.setSelection(viewModel.currentReport.cardReceived)
        notes.text = viewModel.currentReport.notes
    }

    private fun writeToViewModel() {
        viewModel.currentReport.cardReceived = cardSpinner.selectedItemPosition
        viewModel.currentReport.notes = notes.text.toString()
    }

    private fun submit() {
        writeToViewModel()

        teamsInfo[0][submissions][0] = viewModel.currentReport.teamNumber
        teamsInfo[0][submissions][1] = viewModel.currentReport.autoL1
        teamsInfo[0][submissions][1] = viewModel.currentReport.autoL2
        teamsInfo[0][submissions][1] = viewModel.currentReport.autoL3
        teamsInfo[0][submissions][1] = viewModel.currentReport.autoL4
        submissions += 1

        /*
        var teamNumberAlreadyEntered = false
        var currentTeamRow = 0
        for(x in teamsInfo) {
            for (y in x) {
                if (y[0] == viewModel.currentReport.teamNumber) {
                    teamNumberAlreadyEntered = true
                    currentTeamRow = teamsInfo.size
                    break
                }
            }
        }
        if (!teamNumberAlreadyEntered) {
            //teamsInfo.addRow(addColumn(viewModel.currentReport.teamNumber, viewModel.currentReport.autoL1, viewModel.currentReport.autoL2, viewModel.currentReport.autoL3, viewModel.currentReport.autoL4, viewModel.currentReport.teleopL1, viewModel.currentReport.teleopL2, viewModel.currentReport.teleopL3, viewModel.currentReport.teleopL4))
        } else {
            //teamsInfo.addColumn(viewModel.currentReport.teamNumber, viewModel.currentReport.autoL1, viewModel.currentReport.autoL2, viewModel.currentReport.autoL3, viewModel.currentReport.autoL4, viewModel.currentReport.teleopL1, viewModel.currentReport.teleopL2, viewModel.currentReport.teleopL3, viewModel.currentReport.teleopL4))
        }*/



        // If the report isn't already complete, set the timestamp
        // This is in case of editing
        if (viewModel.currentReport.stagesComplete != 4) {
            viewModel.currentReport.unixTimeComplete = (System.currentTimeMillis() / 1000).toInt()
        }

        // Set report as complete
        viewModel.currentReport.stagesComplete = 4

        viewModel.updateDB()
        viewModel.clearReport()

        findNavController().navigate(R.id.nav_scouting_main)
    }

    override fun onPause() {
        super.onPause()

        writeToViewModel()

        viewModel.updateDB()
    }
}