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
    private val btnMinus5: Button
    private val btnPlus5: Button

    init {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.view_counter, this, true)

        // Initialize UI elements
        textView = findViewById(R.id.text_counter)
        btnMinus = findViewById(R.id.btn_minus)
        btnPlus = findViewById(R.id.btn_plus)
        btnMinus5 = findViewById(R.id.btn_minus5)
        btnPlus5 = findViewById(R.id.btn_plus5)

        // Set initial value
        updateText()

        // Set up button click listeners
        btnMinus.setOnClickListener { decrement(1) }
        btnPlus.setOnClickListener { increment(1) }
        btnMinus5.setOnClickListener { decrement(5) }
        btnPlus5.setOnClickListener { increment(5) }
    }

    private fun increment(amount: Int) {
        count += amount;
        updateText()
    }

    private fun decrement(amount: Int) {
        count -= amount;
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