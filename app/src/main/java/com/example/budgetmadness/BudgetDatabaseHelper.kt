package com.example.budgetmadness

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BudgetDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "budget.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CATEGORIES = "categories"
        const val CATEGORY_ID = "_id"
        const val CATEGORY_NAME = "name"

        const val TABLE_EXPENSES = "expenses"
        const val EXPENSE_ID = "_id"
        const val EXPENSE_NAME = "name"
        const val EXPENSE_AMOUNT = "amount"
        const val EXPENSE_PAYMENT_METHOD = "payment_method"
        const val EXPENSE_CATEGORY = "category"
        const val EXPENSE_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_CATEGORIES (
                $CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $CATEGORY_NAME TEXT NOT NULL
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE $TABLE_EXPENSES (
                $EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $EXPENSE_NAME TEXT NOT NULL,
                $EXPENSE_AMOUNT REAL NOT NULL,
                $EXPENSE_PAYMENT_METHOD TEXT NOT NULL,
                $EXPENSE_CATEGORY TEXT NOT NULL,
                $EXPENSE_DATE TEXT NOT NULL
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }

    fun insertCategory(category: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("categoryName", category)
        }
        val result = db.insert("categories", null, values)
        db.close()
        return result != -1L  // true if insert successful
    }

    fun getAllCategories(): List<String> {
        val categories = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $CATEGORY_NAME FROM $TABLE_CATEGORIES", null)
        while (cursor.moveToNext()) {
            categories.add(cursor.getString(0))
        }
        cursor.close()
        db.close()
        return categories
    }

    fun insertExpense(name: String, amount: Double, paymentMethod: String, category: String, date: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(EXPENSE_NAME, name)
            put(EXPENSE_AMOUNT, amount)
            put(EXPENSE_PAYMENT_METHOD, paymentMethod)
            put(EXPENSE_CATEGORY, category)
            put(EXPENSE_DATE, date)
        }
        db.insert(TABLE_EXPENSES, null, values)
        db.close()
    }
}
