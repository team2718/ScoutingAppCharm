package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel

class ScoutingMatchInfo : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    private var teamNumber: TextView? = null
    private var matchNumber: TextView? = null
    private var scoutName: TextView? = null

    private var didLoadReport = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_scouting_match_info, container, false)

        teamNumber = view.findViewById<EditText>(R.id.teamNumber)
        matchNumber = view.findViewById<EditText>(R.id.matchNumber)
        scoutName = view.findViewById<EditText>(R.id.scoutName)

        if (viewModel.currentReport.uid != 0) {
            teamNumber?.text = viewModel.currentReport.teamNumber.toString()
            matchNumber?.text = viewModel.currentReport.matchNumber.toString()
            scoutName?.text = viewModel.currentReport.scoutName
        }

        view.findViewById<Button>(R.id.startReportButton).setOnClickListener {
            viewModel.newReport()

            viewModel.currentReport.matchNumber = getIntFromTextView(matchNumber)
            viewModel.currentReport.teamNumber = getIntFromTextView(teamNumber)
            viewModel.currentReport.scoutName = scoutName?.text.toString()

            viewModel.updateDB()

            findNavController().navigate(R.id.nav_scouting_auto)
        }

        println("View created")

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

        viewModel.currentReport.matchNumber = getIntFromTextView(matchNumber)
        viewModel.currentReport.teamNumber = getIntFromTextView(teamNumber)
        viewModel.currentReport.scoutName = scoutName?.text.toString()

        viewModel.updateDB()
    }
}