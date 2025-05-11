package com.example.budgetmadness

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var dbHelper: BudgetDatabaseHelper
    private lateinit var categoryListView: ListView
    private lateinit var newCategoryInput: EditText
    private lateinit var addCategoryButton: Button
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var categories: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categories)

        logEvent("CategoriesActivity started")

        dbHelper = BudgetDatabaseHelper(this)

        categoryListView = findViewById(R.id.categoryListView)
        newCategoryInput = findViewById(R.id.newCategoryInput)
        addCategoryButton = findViewById(R.id.addCategoryButton)

        // Load categories into list
        categories = dbHelper.getAllCategories().toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categories)
        categoryListView.adapter = adapter

        // Add category button click
        addCategoryButton.setOnClickListener {
            val newCategory = newCategoryInput.text.toString().trim()
            if (newCategory.isNotEmpty()) {
                val success = dbHelper.insertCategory(newCategory)
                if (success) {
                    logEvent("Category added: $newCategory")
                    Toast.makeText(this, "Category added!", Toast.LENGTH_SHORT).show()
                    newCategoryInput.text.clear()
                    loadCategories()
                } else {
                    logEvent("Failed to add category: $newCategory")
                    Toast.makeText(this, "Failed to add. Might already exist.", Toast.LENGTH_SHORT).show()
                }
            } else {
                logEvent("Attempted to add empty category")
                Toast.makeText(this, "Enter a valid category", Toast.LENGTH_SHORT).show()
            }
        }

        // BOTTOM NAV
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_income -> {
                    logEvent("Navigating to IncomeActivity")
                    startActivity(Intent(this, IncomeActivity::class.java))
                    true
                }
                R.id.nav_home -> {
                    logEvent("Navigating to StarterPageActivity")
                    startActivity(Intent(this, StarterPageActivity::class.java))
                    true
                }
                R.id.nav_add -> {
                    logEvent("Navigating to AddExpensesActivity")
                    startActivity(Intent(this, AddExpensesActivity::class.java))
                    true
                }
                R.id.nav_open_menu -> {
                    logEvent("Navigating to MenuActivity")
                    startActivity(Intent(this, MenuActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadCategories() {
        categories.clear()
        categories.addAll(dbHelper.getAllCategories())
        adapter.notifyDataSetChanged()
        logEvent("Categories list reloaded")
    }

    private fun logEvent(message: String) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val logMessage = "$timestamp - $message"
        Log.d("CategoriesActivityLog", logMessage)

        try {
            val file = File(getExternalFilesDir(null), "app_log.txt")
            val writer = FileWriter(file, true)
            writer.appendLine(logMessage)
            writer.close()
        } catch (e: IOException) {
            Log.e("CategoriesActivityLog", "Failed to write log: ${e.message}")
        }
    }
}
