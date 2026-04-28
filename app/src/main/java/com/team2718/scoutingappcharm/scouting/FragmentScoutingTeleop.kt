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
    private lateinit var passRate: RatingBar
    private lateinit var defenseRate: RatingBar

    private lateinit var fuelCheckBox: CheckBox
    private lateinit var passCheckBox: CheckBox
    private lateinit var defCheckBox: CheckBox

    private lateinit var climbSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scouting_teleop, container, false)

        fuelRate = view.findViewById(R.id.fuelRatingBar)
        passRate = view.findViewById(R.id.passRatingBar)
        defenseRate = view.findViewById(R.id.defRatingBar)

        fuelCheckBox = view.findViewById(R.id.fuelCheckBox)
        passCheckBox = view.findViewById(R.id.passCheckBox)
        defCheckBox = view.findViewById(R.id.defCheckBox)

        climbSpinner = view.findViewById(R.id.climbSpinner)

        fuelCheckBox.setOnCheckedChangeListener { _, isChecked ->
            fuelRate.isEnabled = isChecked
            if (!isChecked) {
                fuelRate.rating = 0f
            }
        }

        passCheckBox.setOnCheckedChangeListener { _, isChecked ->
            passRate.isEnabled = isChecked
            if (!isChecked) {
                passRate.rating = 0f
            }
        }

        defCheckBox.setOnCheckedChangeListener { _, isChecked ->
            defenseRate.isEnabled = isChecked
            if (!isChecked) {
                defenseRate.rating = 0f
            }
        }

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
        fuelCheckBox.isChecked = viewModel.currentReport.teleFuelScoredAny
        fuelRate.isEnabled = viewModel.currentReport.teleFuelScoredAny
        fuelRate.rating = viewModel.currentReport.teleFuelScore.toFloat()

        passCheckBox.isChecked = viewModel.currentReport.teleDidPass
        passRate.isEnabled = viewModel.currentReport.teleDidPass
        passRate.rating = viewModel.currentReport.telePassScore.toFloat()

        defCheckBox.isChecked = viewModel.currentReport.teleDidDef
        defenseRate.isEnabled = viewModel.currentReport.teleDidDef
        defenseRate.rating = viewModel.currentReport.teleDefScore.toFloat()

        climbSpinner.setSelection(viewModel.currentReport.climbType)
    }

    private fun writeToViewModel() {
        viewModel.currentReport.teleFuelScoredAny = fuelCheckBox.isChecked
        viewModel.currentReport.teleFuelScore = if (fuelCheckBox.isChecked) fuelRate.rating.roundToInt() else 0

        viewModel.currentReport.teleDidPass = passCheckBox.isChecked
        viewModel.currentReport.telePassScore = if (passCheckBox.isChecked) passRate.rating.roundToInt() else 0

        viewModel.currentReport.teleDidDef = defCheckBox.isChecked
        viewModel.currentReport.teleDefScore = if (defCheckBox.isChecked) defenseRate.rating.roundToInt() else 0

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
