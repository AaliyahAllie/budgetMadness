package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.loginUsername)
        val password = findViewById<EditText>(R.id.loginPassword)
        val loginBtn = findViewById<Button>(R.id.loginBtnFinal)

        loginBtn.setOnClickListener {
            val prefs = getSharedPreferences("users", MODE_PRIVATE)
            val storedPassword = prefs.getString("${username.text}_password", "")

            if (storedPassword == password.text.toString()) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, StarterPageActivity::class.java))  // Redirect to StarterPage
                finish()  // Close the LoginActivity
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
