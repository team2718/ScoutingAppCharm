package com.team2718.scoutingappcharm.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.team2718.scoutingappcharm.R

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var count = 0
    private val textView: TextView
    private val btnMinus: Button
    private val btnPlus: Button

    init {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.view_counter, this, true)

        // Initialize UI elements
        textView = findViewById(R.id.text_counter)
        btnMinus = findViewById(R.id.btn_minus)
        btnPlus = findViewById(R.id.btn_plus)

        // Set initial value
        updateText()

        // Set up button click listeners
        btnMinus.setOnClickListener { decrement() }
        btnPlus.setOnClickListener { increment() }
    }

    private fun increment() {
        count++
        updateText()
    }

    private fun decrement() {
        count--
        if (count < 0) count = 0
        updateText()
    }

    private fun updateText() {
        textView.text = count.toString()
    }

    // Public method to set the value from outside
    fun setValue(value: Int) {
        count = value
        updateText()
    }

    // Public method to set the value from outside
    fun setValue(value: Int?) {
        if (value != null) {
            count = value
        } else {
            count = 0
        }
        updateText()
    }

    // Public method to get the current value
    fun getValue(): Int {
        return count
    }
}