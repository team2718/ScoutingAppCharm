package com.example.scoutingappcharm
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Hashtable
import kotlin.math.max

class MainActivity : ComponentActivity() {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val prematchLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.prematch)
            val endgameLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.Endgame)
            if (prematchLayout.visibility == View.VISIBLE) {
                var x = it.x  // X position of the touch
                var y = it.y  // Y position of the touch
                x = Math.round(x).toFloat()
                y = Math.round(y).toFloat()

                // You can use the touch position here
                Log.d("TouchPosition", "X: ${x / 10}, Y: ${y / 10}")

                //val Xdp = x / (resources.displayMetrics.density)
                //val Ydp = y / (resources.displayMetrics.density)

                val pointToTouch = findViewById<TextView>(R.id.pointToTouch)

                val startingPos = findViewById<TextView>(R.id.StartingPos)

                startingPos.text = "(${x / 10f}, ${y / 10f})"

                pointToTouch.x = clamp(x, 385f, 1240f);
                pointToTouch.y = clamp(y, 275f, 655f);
            }
            if (endgameLayout.visibility == View.VISIBLE) {
                var x = it.x  // X position of the touch
                var y = it.y  // Y position of the touch
                x = Math.round(x).toFloat()
                y = Math.round(y).toFloat()

                // You can use the touch position here
                Log.d("TouchPosition", "X: ${x / 10}, Y: ${y / 10}")

                //val Xdp = x / (resources.displayMetrics.density)
                //val Ydp = y / (resources.displayMetrics.density)

                val pointToTouch2 = findViewById<TextView>(R.id.pointToTouch2)

                val endingPos = findViewById<TextView>(R.id.endPos)

                endingPos.text = "(${x / 10f}, ${y / 10f})"

                pointToTouch2.x = clamp(x, 385f, 1240f);
                pointToTouch2.y = clamp(y, 275f, 655f);
            }
        }
        return super.onTouchEvent(event)
    }

    private fun clamp(value: Float, min: Float, max: Float): Float {
        if (value > max) {
            return max
        }
        if (value < min) {
            return min
        }
        return value;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout) // Set the content view here

        var pageNum = 1

        var tL4ScoredInt = "0"

        val submit = findViewById<Button>(R.id.Submit)

        val prematchLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.prematch)
        val autoLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.auto)
        val teleopLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.Teleop)
        val endgameLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.Endgame)
        val postmatchLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.PostMatch)

        val next1 = findViewById<Button>(R.id.next1)
        val back1 = findViewById<Button>(R.id.back1)

        val PRteamnumber = findViewById<TextInputEditText>(R.id.TeamNumber)
        val PRmatchnumber = findViewById<TextInputEditText>(R.id.MatchNumber)
        val PRstartingpos = findViewById<TextView>(R.id.StartingPos)

        val Amoved = findViewById<CheckBox>(R.id.moved)
        val Adislodged_algae = findViewById<CheckBox>(R.id.Adislodged_algae)
        val AL1Scored = findViewById<TextInputEditText>(R.id.AL1Scored)
        val AL2Scored = findViewById<TextInputEditText>(R.id.AL2Scored)
        val AL3Scored = findViewById<TextInputEditText>(R.id.AL3Scored)
        val AL4Scored = findViewById<Button>(R.id.AL4Scored)
        val Aalgae_barge_scored = findViewById<TextInputEditText>(R.id.Aalgae_barge_scored)
        val Aalgae_processor_scored = findViewById<TextInputEditText>(R.id.Aalgae_processor_scored)
        val Afoul = findViewById<TextInputEditText>(R.id.Afoul)

        val Tfellover = findViewById<CheckBox>(R.id.Tfell_over)
        val Tdied = findViewById<CheckBox>(R.id.Tdied)
        val Tdefended = findViewById<CheckBox>(R.id.Tdefended)
        val Tdislodged_algae = findViewById<CheckBox>(R.id.Tdislodged_algae)
        val TL1Scored = findViewById<TextInputEditText>(R.id.TL1Scored)
        val TL2Scored = findViewById<TextInputEditText>(R.id.TL2Scored)
        val TL3Scored = findViewById<TextInputEditText>(R.id.TL3Scored)
        val TL4Scored = findViewById<TextInputEditText>(R.id.TL4Scored)
        val Talgae_barged_scored = findViewById<TextInputEditText>(R.id.Talgae_barged_scored)
        val Talgae_processor_scored = findViewById<TextInputEditText>(R.id.Talgae_processor_scored)
        val Ttouched_opposing_cage = findViewById<TextInputEditText>(R.id.Ttimes_oppsing_cage)

        val Edefended = findViewById<CheckBox>(R.id.Edefended)
        val Eendpos = findViewById<TextView>(R.id.endPos)

        val POCardsReceived = findViewById<TextInputEditText>(R.id.PCardsReceived)
        val POGeneralNotes = findViewById<TextInputEditText>(R.id.PGeneralNotes)
        val offensiveDropdown = findViewById<Spinner>(R.id.offensiveSkill)
        val defensiveDropdown = findViewById<Spinner>(R.id.defensiveSkill)
        val items = listOf("1", "2", "3", "4", "5")

        setupSpinner(this, offensiveDropdown, items)
        setupSpinner(this, defensiveDropdown, items)

        postmatchLayout.visibility = View.GONE;
        endgameLayout.visibility = View.GONE;
        teleopLayout.visibility = View.GONE
        autoLayout.visibility = View.GONE
        prematchLayout.visibility = View.VISIBLE

        next1.visibility = View.VISIBLE
        back1.visibility = View.GONE

        submit.setOnClickListener {
            val content = "Prematch Team Number: ${PRteamnumber.text}\n" +
                    "Prematch Match Number: ${PRmatchnumber.text}\n" +
                    "Prematch Starting Position: ${PRstartingpos.text}\n" +
                    "\n" +
                    "Auto Moved: ${Amoved.isChecked}\n" +
                    "Auto L1 Scored: ${AL1Scored.text}\n" +
                    "Auto L2 Scored: ${AL2Scored.text}\n" +
                    "Auto L3 Scored: ${AL3Scored.text}\n" +
                    "Auto L4 Scored: ${AL4Scored.text}\n" +
                    "Auto Algae Barge Scored: ${Aalgae_barge_scored.text}\n" +
                    "Auto Algae Processor Scored: ${Aalgae_processor_scored.text}\n" +
                    "Auto Algae Dislodged: ${Adislodged_algae.isChecked}\n" +
                    "Auto Foul: ${Afoul.text}\n" +
                    "\n" +
                    "Teleop Dislodged Algae: ${Tdislodged_algae.isChecked}\n" +
                    "Teleop L1 Scored: ${tL4ScoredInt.toString()}\n" +
                    "Teleop L2 Scored: ${TL2Scored.text}\n" +
                    "Teleop L3 Scored: ${TL3Scored.text}\n" +
                    "Teleop L4 Scored: ${TL4Scored.text}\n" +
                    "Teleop Algae Barge Scored: ${Talgae_barged_scored.text}\n" +
                    "Teleop Algae Processor Scored: ${Talgae_processor_scored.text}\n" +
                    "Teleop Crossed Field/Played Defense: ${Tdefended.isChecked}\n" +
                    "Teleop Fell Over: ${Tfellover.isChecked}\n" +
                    "Teleop How Many Times Did the Robot Touch the Opposing Cage: ${Ttouched_opposing_cage.text}\n" +
                    "Teleop Died: ${Tdied.isChecked}\n" +
                    "\n" +
                    "Endgame End Position: ${Eendpos.text}\n" +
                    "Endgame Defended: ${Edefended.isChecked}\n" +
                    "\n" +
                    "Postmatch Offensive Skill: ${offensiveDropdown.selectedItem}\n" +
                    "Postmatch Defensive Skill: ${defensiveDropdown.selectedItem}\n" +
                    "Postmatch Cards Received: ${POCardsReceived.text}\n" +
                    "Postmatch General Notes: ${POGeneralNotes.text}"

            val name = "Team(${PRteamnumber.text.toString()})MatchNumber(${PRmatchnumber.text.toString()})QRCode.jpg"
            println(PRteamnumber.text)
            println(name)
            generateAndSaveQRCode(this@MainActivity, getPhotoDirectory(), content, name)
            showSnackbar(it, "Saved QR Code to ${getPhotoDirectory()}")
        }

        fun UpdatePage(): Unit {
            if (pageNum == 1) {
                postmatchLayout.visibility = View.GONE;
                endgameLayout.visibility = View.GONE;
                teleopLayout.visibility = View.GONE
                autoLayout.visibility = View.GONE
                prematchLayout.visibility = View.VISIBLE
            } else if (pageNum == 2) {
                postmatchLayout.visibility = View.GONE
                endgameLayout.visibility = View.GONE
                teleopLayout.visibility = View.GONE
                autoLayout.visibility = View.VISIBLE
                prematchLayout.visibility = View.GONE
            } else if (pageNum == 3) {
                postmatchLayout.visibility = View.GONE
                endgameLayout.visibility = View.GONE
                teleopLayout.visibility = View.VISIBLE
                autoLayout.visibility = View.GONE
                prematchLayout.visibility = View.GONE
            } else if (pageNum == 4) {
                postmatchLayout.visibility = View.GONE
                endgameLayout.visibility = View.VISIBLE
                teleopLayout.visibility = View.GONE
                autoLayout.visibility = View.GONE
                prematchLayout.visibility = View.GONE
            } else if (pageNum == 5) {
                postmatchLayout.visibility = View.VISIBLE
                endgameLayout.visibility = View.GONE
                teleopLayout.visibility = View.GONE
                autoLayout.visibility = View.GONE
                prematchLayout.visibility = View.GONE
            } else {
                postmatchLayout.visibility = View.GONE
                endgameLayout.visibility = View.GONE
                teleopLayout.visibility = View.GONE
                autoLayout.visibility = View.GONE
                prematchLayout.visibility = View.GONE
            }
        }

        fun UpdateNextButton(): Unit {
            if (pageNum == 5) {
                next1.visibility = View.GONE
            } else {
                next1.visibility = View.VISIBLE
            }
        }

        fun UpdateBackButton(): Unit {
            if (pageNum == 1) {
                back1.visibility = View.GONE
            } else {
                back1.visibility = View.VISIBLE
            }
        }

        next1.setOnClickListener {
            pageNum += 1
            if (pageNum > 5) {
                pageNum = 5
            }
            UpdatePage()
            UpdateNextButton()
            UpdateBackButton()
        }

        // Handle "Back" button click
        back1.setOnClickListener {
            pageNum -= 1
            if (pageNum < 0) {
                pageNum = 0
            }
            UpdatePage()
            UpdateNextButton()
            UpdateBackButton()
        }


        AL1Scored.setOnClickListener {
            tL4ScoredInt.toInt()
            tL4ScoredInt += 1
            tL4ScoredInt.toString()
            findViewById<TextView>(R.id.AL4Text).text = tL4ScoredInt
        }
    }

    private fun setupSpinner(
        context: Context,
        spinner: Spinner,
        items: List<String>
    ) {
        // Create an ArrayAdapter using the provided list of items and default spinner layout
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Apply the adapter to the spinner
        spinner.adapter = adapter

        // Set up the item selection listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Safeguard against null views
                if (view == null) {
                    // Optionally log or handle the null case
                    return
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun getPhotoDirectory(): File {
        val photoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        return File(photoDirectory, "Camera")
    }


    fun generateAndSaveQRCode(context: Context, directory: File, content: String, name: String) {
        try {
            // Create the QR code bitmap
            val qrCodeBitmap = generateQRCode(content, 500) // QR code size of 500px

            // Ensure the directory exists
            if (!directory.exists()) {
                directory.mkdirs()  // Create the directory if it doesn't exist
            }

            // Create a file to save the QR code image (JPG format)
            val qrCodeFile = File(directory, name)

            // Save the bitmap as a JPG file
            val fileOutputStream = FileOutputStream(qrCodeFile)
            qrCodeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)  // Save as JPEG
            fileOutputStream.flush()
            fileOutputStream.close()

            Log.d("QR Code", "QR Code saved to: ${qrCodeFile.absolutePath}")

            // Trigger the media scanner to scan the new file so it appears in the gallery
            //addToMediaStore(context, qrCodeFile)
            MediaScannerConnection.scanFile(context, arrayOf(qrCodeFile.absolutePath), null, null)

        } catch (e: Exception) {
            Log.e("QR Code", "Error generating or saving QR code: ${e.message}")
        }
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


    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun addToMediaStore(context: Context, file: File) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, file.name) // File name
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES) // For newer versions
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000)
        }

        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            context.contentResolver.openOutputStream(it)?.use { outputStream ->
                FileInputStream(file).use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }


}

