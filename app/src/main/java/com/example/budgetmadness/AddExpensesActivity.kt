package com.example.budgetmadness

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import  java.util.*
import android.app.DatePickerDialog
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddExpensesActivity : AppCompatActivity() {

    private lateinit var expenseNameInput: EditText
    private lateinit var expenseAmountInput: EditText
    private lateinit var paymentMethodInput: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var selectDataButton: Button
    private lateinit var uploadReceiptButton: Button
    private lateinit var addExpenseButton: Button

    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expenses)

        val dbHelper = BudgetDatabaseHelper(this)

        //LINK VIEWS
        expenseNameInput = findViewById(R.id.expenseNameInput)
        expenseAmountInput = findViewById(R.id.expenseAmountInput)
        paymentMethodInput = findViewById(R.id.paymentMethodInput)
        categorySpinner = findViewById(R.id.categorySpinner)
        selectDataButton = findViewById(R.id.selectDateButton)
        uploadReceiptButton = findViewById(R.id.uploadReceiptButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)

        //SPINNER SETUP
        val categories = dbHelper.getAllCategories()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        //DATE PICKER
        selectDataButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$y-${m + 1}-$d"
                selectDataButton.text = selectedDate
            }, year, month, day)

            datePickerDialog.show()
        }


        //RECEIPT BUTTON CLICK


        //ADD EXPENSE
        addExpenseButton.setOnClickListener {
            val name = expenseNameInput.text.toString()
            val amount = expenseAmountInput.text.toString().toDoubleOrNull() ?: 0.0
            val paymentMethod = paymentMethodInput.text.toString()
            val category = categorySpinner.selectedItem?.toString() ?: ""
            val date = selectedDate

            if (name.isNotEmpty() && amount > 0 && paymentMethod.isNotEmpty() && category.isNotEmpty() && date.isNotEmpty()) {
                dbHelper.insertExpense(name, amount, paymentMethod, category, date)
                Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show()
                clearInputs()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
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
    private fun clearInputs() {

        expenseNameInput.text.clear()
        expenseAmountInput.text.clear()
        paymentMethodInput.text.clear()
        categorySpinner.setSelection(0)
        selectDataButton.text = "Select Date"
        selectedDate = ""
    }
}




