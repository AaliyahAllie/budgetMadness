package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var incomeTextView: TextView
   // private lateinit var expensesTextView: TextView
    //private lateinit var balanceTextView: TextView

    private val handler = Handler(Looper.getMainLooper())

    // Runnable to update only the income block
    private val updateIncomeRunnable = object : Runnable {
        override fun run() {
            // Fetch updated income from the database
            val incomeDatabaseHelper = IncomeDatabaseHelper(this@HomeScreenActivity)
            val totalIncome = incomeDatabaseHelper.getTotalIncome()

            // Update the income block with the fetched value
            incomeTextView.text = "+R$totalIncome"

            // Schedule the next income update after 5 seconds
            handler.postDelayed(this, 5000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Initialize views
        incomeTextView = findViewById(R.id.incomeTextView)
        //expensesTextView = findViewById(R.id.expensesTextView)
        //balanceTextView = findViewById(R.id.balanceTextView)

        // Start the periodic update for income
        handler.post(updateIncomeRunnable)

        // Set up bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_income -> {
                    startActivity(Intent(this, IncomeActivity::class.java))
                    true
                }
                R.id.nav_home -> {
                    startActivity(Intent(this, StarterPageActivity::class.java))
                    true
                }
                R.id.nav_add -> {
                    startActivity(Intent(this, AddExpensesActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove any pending posts of the updateIncomeRunnable to prevent memory leaks
        handler.removeCallbacks(updateIncomeRunnable)
    }
}
