package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class BalanceActivity : AppCompatActivity() {

    private lateinit var dbHelper: IncomeDatabaseHelper
    private lateinit var expenseDbHelper: BudgetDatabaseHelper
    private lateinit var totalIncomeText: TextView
    private lateinit var recyclerViewIncome: RecyclerView
    private lateinit var recyclerViewExpenses: RecyclerView
    private lateinit var incomeAdapter: IncomeHistoryAdapter
    private lateinit var expenseAdapter: ExpenseHistoryAdapter

    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 2000L // 2 seconds

    private val incomeUpdater = object : Runnable {
        override fun run() {
            // Get total income from database
            val totalIncome = dbHelper.getTotalIncome()

            // Get the latest expense from database
            val latestExpense = expenseDbHelper.getLatestExpense()

            // Compute the balance: totalIncome - latestExpense
            val balance = totalIncome - latestExpense

            // Display the balance in totalIncomeText
            totalIncomeText.text = "Total Balance: R%.2f".format(balance)

            // Update the balance every 2 seconds
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        dbHelper = IncomeDatabaseHelper(this)
        expenseDbHelper = BudgetDatabaseHelper(this)
        totalIncomeText = findViewById(R.id.text_total_income)
        recyclerViewIncome = findViewById(R.id.recycler_income_history)
        recyclerViewExpenses = findViewById(R.id.recycler_expense_history)

        // Set up the income recycler view
        val incomeList = dbHelper.getAllIncomeHistory()
        incomeAdapter = IncomeHistoryAdapter(incomeList)
        recyclerViewIncome.layoutManager = LinearLayoutManager(this)
        recyclerViewIncome.adapter = incomeAdapter

        // Set up the expense recycler view
        val expenseList = expenseDbHelper.getAllExpenses()
        expenseAdapter = ExpenseHistoryAdapter(expenseList)
        recyclerViewExpenses.layoutManager = LinearLayoutManager(this)
        recyclerViewExpenses.adapter = expenseAdapter

        // Bottom navigation setup
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

    override fun onResume() {
        super.onResume()
        handler.post(incomeUpdater)  // Start the incomeUpdater when the activity is resumed
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(incomeUpdater)  // Stop the incomeUpdater when the activity is paused
    }
}
