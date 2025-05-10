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
    private lateinit var expenseTextView: TextView
    private lateinit var balanceTextView: TextView

    private val handler = Handler(Looper.getMainLooper())

    private val updateUIRunnable = object : Runnable {
        override fun run() {
            val incomeDb = IncomeDatabaseHelper(this@HomeScreenActivity)
            val expenseDb = BudgetDatabaseHelper(this@HomeScreenActivity)

            // Get values
            val latestIncome = incomeDb.getLatestIncome()
            val totalIncome = incomeDb.getTotalIncome()
            val latestExpense = expenseDb.getLatestExpense()

            // Display latest income and expense
            incomeTextView.text = "+R$latestIncome"
            expenseTextView.text = "-R$latestExpense"

            // Compute balance: totalIncome - latestExpense
            val balance = totalIncome - latestExpense
            balanceTextView.text = "R$balance"

            // Refresh every 5 seconds
            handler.postDelayed(this, 5000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Connect TextViews
        incomeTextView = findViewById(R.id.incomeTextView)
        expenseTextView = findViewById(R.id.textViewExpenses)
        balanceTextView = findViewById(R.id.textViewBalance)

        // Start UI updates
        handler.post(updateUIRunnable)

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
                    startActivity(Intent(this, MenuActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateUIRunnable)
    }
}
