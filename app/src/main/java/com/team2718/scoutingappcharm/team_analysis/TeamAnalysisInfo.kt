package com.team2718.scoutingappcharm.team_analysis

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import com.team2718.scoutingappcharm.data_entity.ScoutingReport
import com.team2718.scoutingappcharm.scouting.FragmentScoutingPostMatch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Hashtable
import java.util.Locale

class TeamAnalysisInfo : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    val date_format_full = java.text.SimpleDateFormat("EEE, MMM d, h:mm a")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_analysis_info, container, false)

        fillView(view)

        return view
    }

    fun fillView(view: View) {

        var reportIndex = viewModel.viewReportIndex
        var report: ScoutingReport = viewModel.viewReportList.orEmpty()[reportIndex]

        var autoL1 = 7 //teamsInfo[0][0][0]

        view.findViewById<TextView>(R.id.qr_label).text = String.format(Locale.US,"Team %s", report.teamNumber, report.matchNumber)
        view.findViewById<TextView>(R.id.qr_autoL1_label).text = String.format(Locale.US, "Auto L1 Average: %s", report.autoL1, report.autoL1)
        view.findViewById<TextView>(R.id.qr_autoL2_label).text = String.format(Locale.US, "Auto L2 Average: %s", report.autoL2, report.autoL2)
        view.findViewById<TextView>(R.id.qr_autoL3_label).text = String.format(Locale.US, "Auto L3 Average: %s", report.autoL3, report.autoL3)
        view.findViewById<TextView>(R.id.qr_autoL4_label).text = String.format(Locale.US, "Auto L4 Average: %s", autoL1, autoL1)

        if (reportIndex > 0) {
            view.findViewById<Button>(R.id.prev_button).isEnabled = true
            view.findViewById<Button>(R.id.prev_button).setOnClickListener {
                viewModel.viewReportIndex = reportIndex - 1
                fillView(view)
            }
        } else {
            view.findViewById<Button>(R.id.prev_button).isEnabled = false
        }

        if (reportIndex < viewModel.viewReportList.orEmpty().size - 1) {
            view.findViewById<Button>(R.id.next_button).isEnabled = true
            view.findViewById<Button>(R.id.next_button).setOnClickListener{
                viewModel.viewReportIndex = reportIndex + 1
                fillView(view)
            }
        } else {
            view.findViewById<Button>(R.id.next_button).isEnabled = false
        }

        val json = Json { encodeDefaults = true }

    }

    fun generateQRCode(content: String, size: Int): Bitmap {
        val qrCodeWriter = QRCodeWriter()


        // Define encoding hints
        val hints = Hashtable<EncodeHintType, Any>()
        hints[EncodeHintType.MARGIN] = 1 // Optional: margin around the QR code


        // Create the QR code matrix
        val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints)


        // Convert the matrix to a Bitmap
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)


        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }


        return bitmap
    }


    override fun onPause() {
        super.onPause()
    }
}