package com.team2718.scoutingappcharm.sync

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        var textBoxThing = view.findViewById<TextView>(R.id.textviewtest)

        return view
    }

    override fun onPause() {
        super.onPause()

        println("Test!!")
    }
}