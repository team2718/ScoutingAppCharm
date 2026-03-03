package com.team2718.scoutingappcharm.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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

        var assignmentSpinner = view.findViewById<Spinner>(R.id.assignmentSpinner)

        assignmentSpinner.setSelection(viewModel.getAssignmentId())

        assignmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setAssignment(assignmentSpinner.selectedItem.toString(), assignmentSpinner.selectedItemPosition);
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }

        return view
    }

    override fun onPause() {
        super.onPause()
    }
}