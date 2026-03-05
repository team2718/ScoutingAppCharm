package com.team2718.scoutingappcharm.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel


class Settings : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val assignmentSpinner = view.findViewById<Spinner>(R.id.assignmentSpinner)
        val saveButton = view.findViewById<Button>(R.id.settingsSaveButton)

        assignmentSpinner.setSelection(viewModel.getAssignmentId())

        saveButton.setOnClickListener {
            viewModel.setAssignment(assignmentSpinner.selectedItem.toString(), assignmentSpinner.selectedItemPosition)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
    }
}
