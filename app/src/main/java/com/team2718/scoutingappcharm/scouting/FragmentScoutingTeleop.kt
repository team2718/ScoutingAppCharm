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

class FragmentScoutingTeleop : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_scouting_teleop, container, false)

        if (viewModel.doPageSkipping && viewModel.currentReport.stagesComplete!! >= 3) {
//            findNavController().navigate(R.id.nav_scouting_auto)
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

        // TODO: Load in data to fields

        viewModel.updateDB()
    }
}