package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel

class FragmentScoutingMain : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_main, container, false)

        if (viewModel.currentReport.stagesComplete == 0) { // Only "continue" reports which have at least entered match/team info
            view.findViewById<Button>(R.id.continueReportButton).isEnabled = false
            view.findViewById<TextView>(R.id.continueReportText).text = ""
        } else {
            view.findViewById<Button>(R.id.continueReportButton).isEnabled = true
            view.findViewById<TextView>(R.id.continueReportText).text =
                getString(
                    R.string.continue_report_subtext,
                    viewModel.currentReport.matchNumber,
                    viewModel.currentReport.teamNumber
                )
        }

        view.findViewById<Button>(R.id.matchInfoNext).setOnClickListener {
            viewModel.shouldMakeNewReport = true
            findNavController().navigate(R.id.nav_scouting_match_info)
        }

        view.findViewById<Button>(R.id.continueReportButton).setOnClickListener {
            viewModel.shouldMakeNewReport = false
            viewModel.doPageSkipping = true
            findNavController().navigate(R.id.nav_scouting_match_info)
        }

        return view
    }
}