package com.example.budgetmadness


import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerUserBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerUserBtn = findViewById(R.id.registerUserBtn)

        // Register button click listener
        registerUserBtn.setOnClickListener {
            // Get input values
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val password = passwordInput.text.toString()

            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()
                || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save user data using SharedPreferences
            val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("firstName", firstName)
            editor.putString("lastName", lastName)
            editor.putString("username", username)
            editor.putString("email", email)
            editor.putString("phone", phone)
            editor.putString("password", password)
            editor.apply()

            // Display success message
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

            // Optional: Navigate back to login or home screen
            finish()
        }
    }
}
