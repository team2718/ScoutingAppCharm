package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel

class FragmentScoutingMatchInfo : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_scouting_match_info, container, false)

        var teamNumber = view.findViewById<EditText>(R.id.teamNumber)
        var matchNumber = view.findViewById<EditText>(R.id.matchNumber)
        var scoutName = view.findViewById<EditText>(R.id.scoutName)
        var alliance = view.findViewById<Spinner>(R.id.alliance)
        var startingPosition = view.findViewById<Spinner>(R.id.starting_position)

        if (!viewModel.shouldMakeNewReport && viewModel.currentReport.stagesComplete != 0) {
            teamNumber.setText(viewModel.currentReport.teamNumber.toString())
            matchNumber.setText(viewModel.currentReport.matchNumber.toString())
            scoutName.setText(viewModel.currentReport.scoutName)
            alliance.setSelection(viewModel.currentReport.alliance)
            startingPosition.setSelection(viewModel.currentReport.startingPosition)
        }

        view.findViewById<Button>(R.id.matchInfoNext).setOnClickListener {
            var error = false

            if (getIntFromTextView(teamNumber) == 0) {
                teamNumber.error = "Please enter a valid team number"
                error = true
            }

            if (getIntFromTextView(matchNumber) == 0) {
                matchNumber.error = "Please enter a valid match number"
                error = true
            }

            if (scoutName.text.toString() == "") {
                scoutName.error = "Please enter a valid scout name"
                error = true
            }

            if (error) {
                return@setOnClickListener
            }

            if (viewModel.shouldMakeNewReport) {
                viewModel.newReport()
                viewModel.shouldMakeNewReport = false
            }

            viewModel.currentReport.matchNumber = getIntFromTextView(matchNumber)
            viewModel.currentReport.teamNumber = getIntFromTextView(teamNumber)
            viewModel.currentReport.scoutName = scoutName?.text.toString()
            viewModel.currentReport.startingPosition = startingPosition.selectedItemPosition
            viewModel.currentReport.alliance = alliance.selectedItemPosition

            // Increment stages complete
            if (viewModel.currentReport.stagesComplete == 0) {
                viewModel.currentReport.stagesComplete = 1
            }

            viewModel.updateDB()

            findNavController().navigate(R.id.nav_scouting_auto)
        }

        if (viewModel.doPageSkipping && viewModel.currentReport.stagesComplete >= 1) {
            findNavController().navigate(R.id.nav_scouting_auto)
        } else {
            viewModel.doPageSkipping = false
        }

        return view
    }

    private fun getIntFromTextView(textView: TextView?): Int {
        val rawText = textView?.text.toString()
        if (rawText == "")
            return 0
        else
            return Integer.valueOf(rawText)
    }

    override fun onPause() {
        super.onPause()
    }
}