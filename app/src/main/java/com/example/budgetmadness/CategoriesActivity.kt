package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.*

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
                    Toast.makeText(this, "Category added!", Toast.LENGTH_SHORT).show()
                    newCategoryInput.text.clear()
                    loadCategories()
                } else {
                    Toast.makeText(this, "Failed to add. Might already exist.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter a valid category", Toast.LENGTH_SHORT).show()
            }
        }

        //BOTTOM NAV
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_open_menu -> {
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
    }
}

