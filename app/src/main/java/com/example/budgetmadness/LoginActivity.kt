package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameField = findViewById<EditText>(R.id.loginUsername)
        val passwordField = findViewById<EditText>(R.id.loginPassword)
        val loginBtn = findViewById<Button>(R.id.loginBtnFinal)

        loginBtn.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString()

            val prefs = getSharedPreferences("users", MODE_PRIVATE)
            val storedPassword = prefs.getString("${username}_password", "")

            if (storedPassword == password) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                // ✅ Redirect to HomeActivity

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
