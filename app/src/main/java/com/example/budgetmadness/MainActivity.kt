package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var registerBtn: Button
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons
        registerBtn = findViewById(R.id.registerBtn)
        loginBtn = findViewById(R.id.loginBtn)

        // Check if the user is already logged in
        val prefs = getSharedPreferences("users", MODE_PRIVATE)
        val loggedIn = prefs.getBoolean("loggedIn", false)

        if (loggedIn) {
            // If the user is logged in, go directly to HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish()  // Close MainActivity
        }

        // Navigate to RegisterActivity when Register button is clicked
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Navigate to LoginActivity when Login button is clicked
        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
