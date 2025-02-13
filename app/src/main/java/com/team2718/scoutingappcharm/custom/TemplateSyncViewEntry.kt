package com.team2718.scoutingappcharm.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.team2718.scoutingappcharm.R

class TemplateSyncViewEntry @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.template_sync_view_entry, this, true)
    }
}