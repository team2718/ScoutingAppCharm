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

    val date_format = java.text.SimpleDateFormat("EEE, MMM d, h:mm a")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sync_view_matches, container, false)

        var linlayout = view.findViewById<LinearLayout>(R.id.matches_layout)

        var reports = viewModel.getCompleteReports()

        for (report in reports) {
            var child =
                layoutInflater.inflate(R.layout.template_sync_view_entry, linlayout, false)

            var button = child.findViewById<Button>(R.id.entry_template)

            button.text = String.format(
                Locale.US,
                "Match %d  Team %d",
                report.matchNumber,
                report.teamNumber
            )

            button.setOnClickListener{
                var popupview = layoutInflater.inflate(R.layout.qrcode_popup, container, false)

                popupview.findViewById<TextView>(R.id.qr_label).text = String.format(Locale.US,"Match %s  Team %s", report.matchNumber, report.teamNumber)
                popupview.findViewById<TextView>(R.id.qr_time_label).text = date_format.format(report.unixTimeComplete.toLong() * 1000)

                val json = Json { encodeDefaults = true }

                popupview.findViewById<ImageView>(R.id.qr_image).setImageBitmap(generateQRCode(json.encodeToString(report), 400))

                // create the popup window
                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true // lets taps outside the popup also dismiss it
                var popupwindow = PopupWindow(popupview, width, height, focusable)

                popupwindow.animationStyle = R.style.popup_window_animation
                popupwindow.showAtLocation(view, Gravity.CENTER, 0, 0)

                // dismiss the popup window when touched
                popupview.setOnClickListener {
                    popupwindow.dismiss()
                }
            }

            linlayout.addView(child)
        }

        return view
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

        println("Test!!")
    }
}