package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.usernameInput)
        val password = findViewById<EditText>(R.id.passwordInput)
        val firstName = findViewById<EditText>(R.id.firstNameInput)
        val lastName = findViewById<EditText>(R.id.lastNameInput)
        val email = findViewById<EditText>(R.id.emailInput)
        val phone = findViewById<EditText>(R.id.phoneInput)
        val registerBtn = findViewById<Button>(R.id.registerUserBtn)

        registerBtn.setOnClickListener {
            val user = username.text.toString().trim()
            val pass = password.text.toString().trim()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                // Save credentials (simplified for demo purposes)
                val prefs = getSharedPreferences("users", MODE_PRIVATE)
                prefs.edit().putString("${user}_password", pass).apply()

                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()

                // ✅ Redirect to login screen
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
