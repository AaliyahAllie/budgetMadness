package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

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
            val first = firstName.text.toString().trim()
            val last = lastName.text.toString().trim()
            val mail = email.text.toString().trim()
            val phoneNum = phone.text.toString().trim()

            if (user.isNotEmpty() && pass.isNotEmpty() && first.isNotEmpty() && last.isNotEmpty() && mail.isNotEmpty() && phoneNum.isNotEmpty()) {
                // Save user data to database
                dbHelper.addUser(user, pass, first, last, mail, phoneNum)

                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()

                // ✅ Redirect to login screen
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
