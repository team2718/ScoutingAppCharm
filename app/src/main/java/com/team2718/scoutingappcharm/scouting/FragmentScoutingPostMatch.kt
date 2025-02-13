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

    private lateinit var counterL1: CounterView
    private lateinit var counterL2: CounterView
    private lateinit var counterL3: CounterView
    private lateinit var counterL4: CounterView

    private lateinit var counterProcessed: CounterView
    private lateinit var counterBarged: CounterView
    private lateinit var counterHuman: CounterView

    private lateinit var hangSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_post_match, container, false)

        populateView()

        view.findViewById<Button>(R.id.submit).setOnClickListener { submit(view.findViewById<EditText>(R.id.text_notes).text.toString()) }

        // This is the last page, stop skipping
        viewModel.doPageSkipping = false

        return view
    }

    private fun populateView() {

    }

    private fun writeToViewModel() {

    }

    private fun submit(notes: String) {
        writeToViewModel()

        // Set report as complete
        viewModel.currentReport.stagesComplete = 4
        viewModel.currentReport.unixTimeComplete = (System.currentTimeMillis() / 1000).toInt()
        viewModel.currentReport.notes = notes

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