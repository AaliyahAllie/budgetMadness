package com.example.budgetmadness

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StarterPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter_page)

        // Button for getting started
        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            Toast.makeText(this, "Getting started!", Toast.LENGTH_SHORT).show()
        }
    }
}
