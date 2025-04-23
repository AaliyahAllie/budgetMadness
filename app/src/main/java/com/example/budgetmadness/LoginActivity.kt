package com.example.budgetmadness

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginBtnFinal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginUsername = findViewById(R.id.loginUsername)
        loginPassword = findViewById(R.id.loginPassword)
        loginBtnFinal = findViewById(R.id.loginBtnFinal)

        loginBtnFinal.setOnClickListener {
            val username = loginUsername.text.toString().trim()
            val password = loginPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Load stored credentials from SharedPreferences
            val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val savedUsername = prefs.getString("username", null)
            val savedPassword = prefs.getString("password", null)

            if (username == savedUsername && password == savedPassword) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                // You can navigate to another screen here (e.g., HomeActivity)
            } else {
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
