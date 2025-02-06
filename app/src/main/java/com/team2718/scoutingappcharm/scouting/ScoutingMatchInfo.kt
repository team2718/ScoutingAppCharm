package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel

class ScoutingMatchInfo : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    var teamNumber: TextView? = null

    var didLoadReport = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_scouting_match_info, container, false)

        teamNumber = view.findViewById<EditText>(R.id.TeamNumber)

        if (viewModel.currentReport.uid != 0) {
            teamNumber?.text = viewModel.currentReport.teamNumber.toString()
            didLoadReport = true;
        }

        println("View created")

        return view
    }

    override fun onPause() {
        super.onPause()

        // Update Database
        if (!didLoadReport)
            return

        val teamNumberText = teamNumber?.text.toString()
        if (teamNumberText == "")
            viewModel.currentReport.teamNumber = 0
        else
            viewModel.currentReport.teamNumber = Integer.valueOf(teamNumberText)
        viewModel.updateDB()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}