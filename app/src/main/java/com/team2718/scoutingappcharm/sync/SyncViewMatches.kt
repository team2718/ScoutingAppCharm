package com.team2718.scoutingappcharm.sync

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import java.util.Locale

class SyncViewMatches : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sync_view_matches, container, false)

        var linlayout = view.findViewById<LinearLayout>(R.id.matches_layout)

        var reports = viewModel.getCompleteReports()

        for (i in 1..100) {
            for (report in reports) {
                var child =
                    layoutInflater.inflate(R.layout.template_sync_view_entry, linlayout, false)
                child.findViewById<TextView>(R.id.entry_template).text = String.format(
                    Locale.US,
                    "Match %d  Team %d",
                    report.matchNumber,
                    report.teamNumber
                )
                linlayout.addView(child)
            }
        }

        return view
    }

    override fun onPause() {
        super.onPause()

        println("Test!!")
    }
}