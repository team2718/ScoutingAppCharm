package com.team2718.scoutingappcharm.sync

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Hashtable
import java.util.Locale


class SyncViewMatches : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()
    val date_format_short = java.text.SimpleDateFormat("EEE h:mm a")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sync_view_matches, container, false)

        var linlayout = view.findViewById<LinearLayout>(R.id.matches_layout)

        var reports = viewModel.getCompleteReports()

        reports.forEachIndexed{ index, report ->
            var child =
                layoutInflater.inflate(R.layout.template_sync_view_entry, linlayout, false)

            var button = child.findViewById<Button>(R.id.entry_template)
            button.text = String.format(
                Locale.US,
                "Match %d  Team %d",
                report.matchNumber,
                report.teamNumber
            )

            var dateView = child.findViewById<TextView>(R.id.date)
            dateView.text =  date_format_short.format(report.unixTimeComplete.toLong() * 1000)

            button.setOnClickListener{
                viewModel.viewReportIndex = index
                findNavController().navigate(R.id.nav_sync_qr_code)
            }

            linlayout.addView(child)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
    }
}