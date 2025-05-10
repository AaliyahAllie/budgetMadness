package com.example.budgetmadness


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class IncomeActivity : AppCompatActivity() {

    private lateinit var editCashIncome: EditText
    private lateinit var editCardIncome: EditText
    private lateinit var btnSave: Button
    private lateinit var dbHelper: IncomeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        editCashIncome = findViewById(R.id.edit_cash_income)
        editCardIncome = findViewById(R.id.edit_card_income)
        btnSave = findViewById(R.id.btn_save)

        dbHelper = IncomeDatabaseHelper(this)

        btnSave.setOnClickListener {
            val cashStr = editCashIncome.text.toString()
            val cardStr = editCardIncome.text.toString()

            if (cashStr.isBlank() || cardStr.isBlank()) {
                Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val cash = cashStr.toDouble()
                val card = cardStr.toDouble()
                dbHelper.insertIncome(cash, card)
                Toast.makeText(this, "Income saved successfully!", Toast.LENGTH_SHORT).show()
                editCashIncome.text.clear()
                editCardIncome.text.clear()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
            }
        }
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
}