package com.example.budgetmadness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class StarterPageActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter_page)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        // Set the Toolbar as the app bar
        setSupportActionBar(toolbar)

        // Initialize and attach toggle to DrawerLayout
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Navigation menu item clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            Toast.makeText(this, "Clicked: ${menuItem.title}", Toast.LENGTH_SHORT).show()
            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if (this !is StarterPageActivity)
                        startActivity(Intent(this, StarterPageActivity::class.java))
                }
                R.id.nav_add_expenses -> startActivity(Intent(this, AddExpensesActivity::class.java))
                R.id.nav_expense_view -> startActivity(Intent(this, ExpenseViewActivity::class.java))
                R.id.nav_categories -> startActivity(Intent(this, CategoriesActivity::class.java))
                R.id.nav_budget -> startActivity(Intent(this, BudgetActivity::class.java))
                R.id.nav_income -> startActivity(Intent(this, IncomeActivity::class.java))
                R.id.nav_balance -> startActivity(Intent(this, BalanceActivity::class.java))
                R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // "Get Started" button click
        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            Toast.makeText(this, "Getting Started!", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle back press to close drawer first
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
