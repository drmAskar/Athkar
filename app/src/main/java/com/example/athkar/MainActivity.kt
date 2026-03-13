package com.example.athkar

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private var counter = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val counterText: TextView = findViewById(R.id.counterText)
        val incrementButton: Button = findViewById(R.id.incrementButton)
        val decrementButton: Button = findViewById(R.id.decrementButton)
        val resetButton: Button = findViewById(R.id.resetButton)
        
        updateCounterText(counterText)
        
        incrementButton.setOnClickListener {
            counter++
            updateCounterText(counterText)
        }
        
        decrementButton.setOnClickListener {
            counter--
            updateCounterText(counterText)
        }
        
        resetButton.setOnClickListener {
            counter = 0
            updateCounterText(counterText)
        }
    }
    
    private fun updateCounterText(textView: TextView) {
        textView.text = counter.toString()
    }
}
