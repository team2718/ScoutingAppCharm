package com.team2718.scoutingappcharm.sync

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
import com.team2718.scoutingappcharm.MainActivity
import com.team2718.scoutingappcharm.R
import com.team2718.scoutingappcharm.SharedViewModel
import com.team2718.scoutingappcharm.data_entity.ScoutingReport
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Hashtable
import java.util.Locale


class SyncQRCode : Fragment() {

    val viewModel: SharedViewModel by activityViewModels()

    val date_format_full = java.text.SimpleDateFormat("EEE, MMM d, h:mm a")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sync_qrcode, container, false)

        fillView(view)

        return view
    }

    fun fillView(view: View) {

        var reportIndex = viewModel.viewReportIndex
        var report: ScoutingReport = viewModel.viewReportList.orEmpty()[reportIndex]

        view.findViewById<TextView>(R.id.qr_label).text = String.format(Locale.US,"Match %s  Team %s", report.matchNumber, report.teamNumber)
        view.findViewById<TextView>(R.id.qr_time_label).text = date_format_full.format(report.unixTimeComplete.toLong() * 1000)
        view.findViewById<TextView>(R.id.qr_scout_label).text = String.format(Locale.US, "Scout: %s", report.scoutName)

        view.findViewById<Button>(R.id.delete_button).setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(view.context)
            alertDialogBuilder.setCancelable(true)
            alertDialogBuilder.setTitle("Delete Report")
            alertDialogBuilder.setMessage("Are you sure you want to delete this report?")
            alertDialogBuilder.setPositiveButton("Delete",
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.deleteReport(report)
                    findNavController().popBackStack()
                })
            alertDialogBuilder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->})

            val dialog: AlertDialog = alertDialogBuilder.create()
            dialog.show()
        }

        view.findViewById<Button>(R.id.edit_button).setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(view.context)
            alertDialogBuilder.setCancelable(true)
            alertDialogBuilder.setTitle("Edit Report")
            alertDialogBuilder.setMessage("Are you sure you want to edit this report?")
            alertDialogBuilder.setPositiveButton("Edit",
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.currentReport = report
                    findNavController().popBackStack()
                    viewModel.bottomNavigationView.selectedItemId = R.id.nav_scouting
                    viewModel.doPageSkipping = false
                    viewModel.shouldMakeNewReport = false
                    findNavController().navigate(R.id.nav_scouting_match_info)
                })
            alertDialogBuilder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->})

            val dialog: AlertDialog = alertDialogBuilder.create()
            dialog.show()
        }

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

        view.findViewById<ImageView>(R.id.qr_image).setImageBitmap(generateQRCode(json.encodeToString(report), 400))

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