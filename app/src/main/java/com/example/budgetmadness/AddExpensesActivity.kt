package com.example.budgetmadness

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import java.util.*

class AddExpensesActivity : AppCompatActivity() {

    private lateinit var expenseNameInput: EditText
    private lateinit var expenseAmountInput: EditText
    private lateinit var paymentMethodInput: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var selectDataButton: Button
    private lateinit var uploadReceiptButton: Button
    private lateinit var addExpenseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expenses)

        val dbHelper = ExpenseDatabaseHelper(this)
        val categories = dbHelper.getAllCategories()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        //LINK VIEWS
        expenseNameInput = findViewById(R.id.expenseNameInput)
        expenseAmountInput = findViewById(R.id.expenseAmountInput)
        paymentMethodInput = findViewById(R.id.paymentMethodInput)
        categorySpinner = findViewById(R.id.categorySpinner)
        selectDataButton = findViewById(R.id.selectDateButton)
        uploadReceiptButton = findViewById(R.id.uploadReceiptButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)

        //SPINNER SETUP


        //DATE PICKER


        //ADD EXPENSE


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}