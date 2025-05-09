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
    private lateinit var balanceTextView: TextView

    private val handler = Handler(Looper.getMainLooper())

    // Runnable to update income and balance
    private val updateIncomeRunnable = object : Runnable {
        override fun run() {
            val incomeDatabaseHelper = IncomeDatabaseHelper(this@HomeScreenActivity)

            // Get latest income and total income
            val latestIncome = incomeDatabaseHelper.getLatestIncome()
            val totalIncome = incomeDatabaseHelper.getTotalIncome()

            // Update views
            incomeTextView.text = "+R$latestIncome"
            balanceTextView.text = "Total Income: R$totalIncome"

            // Repeat after 5 seconds
            handler.postDelayed(this, 5000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Initialize views
        incomeTextView = findViewById(R.id.incomeTextView)
        balanceTextView = findViewById(R.id.textViewBalance)

        // Start updates
        handler.post(updateIncomeRunnable)

        // Bottom navigation
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
                R.id.nav_open_menu -> {
                    startActivity(Intent(this, AddExpensesActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateIncomeRunnable)
    }
}
