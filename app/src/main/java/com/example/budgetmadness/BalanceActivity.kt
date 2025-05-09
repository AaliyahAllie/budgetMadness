package com.example.budgetmadness

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BalanceActivity : AppCompatActivity() {

    private lateinit var dbHelper: IncomeDatabaseHelper
    private lateinit var totalIncomeText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IncomeHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        dbHelper = IncomeDatabaseHelper(this)
        totalIncomeText = findViewById(R.id.text_total_income)
        recyclerView = findViewById(R.id.recycler_income_history)

        val totalIncome = dbHelper.getTotalIncome()
        totalIncomeText.text = "Total Balance: R%.2f".format(totalIncome)

        val incomeList = dbHelper.getAllIncomeHistory()
        adapter = IncomeHistoryAdapter(incomeList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
