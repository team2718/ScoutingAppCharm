package com.team2718.scoutingappcharm.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.team2718.scoutingappcharm.R

class QRCodePopup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.qrcode_popup, this, true)
    }
}