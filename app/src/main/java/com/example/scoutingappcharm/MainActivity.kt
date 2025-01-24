package com.example.scoutingappcharm

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream
import java.util.Hashtable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the content view here

        // Inside your Activity or Fragment
        val teamNumber = findViewById<EditText>(R.id.TeamNumber)
        val submit = findViewById<Button>(R.id.Submit)
        val matchNumber = findViewById<EditText>(R.id.MatchNumber)
        val startingPos = findViewById<EditText>(R.id.StartingPos)
        val moved = findViewById<CheckBox>(R.id.Moved)

        submit.setOnClickListener {
            showSnackbar(it, "Made a QR Code at ${getPhotoDirectory()}")
            // Pass `this@MainActivity` as the Context to the function
            val content = "Team Number: ${teamNumber.text} \n" +
                    "Match Number: ${matchNumber.text} \n" +
                    "Starting Position: ${startingPos.text} \n" +
                    "Moved: ${moved.isChecked}"
            generateAndSaveQRCode(this@MainActivity, getPhotoDirectory(), content, "Team(${teamNumber.text})MatchNumber(${matchNumber.text})QRCode.jpg")
        }
    }
}

fun getPhotoDirectory(): File {
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
        MediaScannerConnection.scanFile(
            context, // Pass the context here
            arrayOf(qrCodeFile.absolutePath),
            arrayOf("image/jpeg"),
            null
        )

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
