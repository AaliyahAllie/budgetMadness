package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StarterPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter_page)

        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            Toast.makeText(this, "Get Started Clicked!", Toast.LENGTH_SHORT).show()

            // ✅ Start HomeScreenActivity
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        }
    }
}
