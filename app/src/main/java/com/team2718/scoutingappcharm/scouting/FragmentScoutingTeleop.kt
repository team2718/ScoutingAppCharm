package com.team2718.scoutingappcharm.scouting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import com.team2718.scoutingappcharm.custom.CounterView
import kotlin.math.roundToInt

class FragmentScoutingTeleop : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    private lateinit var fuelRate: RatingBar
    private lateinit var accRate: RatingBar
    private lateinit var passRate: RatingBar
    private lateinit var defenseRate: RatingBar

    private lateinit var climbSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_teleop, container, false)

        fuelRate = view.findViewById(R.id.fuelRatingBar)
        accRate = view.findViewById(R.id.accRatingBar)
        passRate = view.findViewById(R.id.passRatingBar)
        defenseRate = view.findViewById(R.id.defRatingBar)

        climbSpinner = view.findViewById(R.id.climbSpinner)

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
        fuelRate.rating = viewModel.currentReport.teleFuelRateScore.toFloat()
        accRate.rating = viewModel.currentReport.teleAccScore.toFloat()
        passRate.rating = viewModel.currentReport.telePassScore.toFloat()
        defenseRate.rating = viewModel.currentReport.teleDefScore.toFloat()

        climbSpinner.setSelection(viewModel.currentReport.climbType)
    }

    private fun writeToViewModel() {
        viewModel.currentReport.teleFuelRateScore = fuelRate.rating.roundToInt()
        viewModel.currentReport.teleAccScore = accRate.rating.roundToInt()
        viewModel.currentReport.telePassScore = passRate.rating.roundToInt()
        viewModel.currentReport.teleDefScore = defenseRate.rating.roundToInt()

        viewModel.currentReport.climbType = climbSpinner.selectedItemPosition
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