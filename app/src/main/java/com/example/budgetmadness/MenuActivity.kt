package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.btnHome).setOnClickListener {
            startActivity(Intent(this, HomeScreenActivity::class.java))
        }

        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<Button>(R.id.btnViewExpenses).setOnClickListener {
            startActivity(Intent(this, ExpenseViewActivity::class.java))
        }

        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpensesActivity::class.java))
        }

        findViewById<Button>(R.id.btnIncome).setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }

        findViewById<Button>(R.id.btnBalance).setOnClickListener {
            startActivity(Intent(this, BalanceActivity::class.java))
        }

        findViewById<Button>(R.id.btnBudget).setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }

        findViewById<Button>(R.id.btnCategories).setOnClickListener {
            startActivity(Intent(this, CategoriesActivity::class.java))
        }
    }
}
