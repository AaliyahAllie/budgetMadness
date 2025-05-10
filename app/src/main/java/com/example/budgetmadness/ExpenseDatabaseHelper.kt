package com.example.budgetmadness

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 1

        // Categories Table
        const val TABLE_CATEGORY = "Category"
        const val COLUMN_CATEGORY_ID = "id"
        const val COLUMN_CATEGORY_NAME = "name"

        // Expenses Table
        const val TABLE_EXPENSE = "Expense"
        const val COLUMN_EXPENSE_ID = "id"
        const val COLUMN_EXPENSE_NAME = "name"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_METHOD = "method"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"
        const val COLUMN_RECEIPT = "receipt"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createCategoryTable = """
            CREATE TABLE $TABLE_CATEGORY (
                $COLUMN_CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY_NAME TEXT NOT NULL
            )
        """.trimIndent()

        val createExpenseTable = """
            CREATE TABLE $TABLE_EXPENSE (
                $COLUMN_EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EXPENSE_NAME TEXT NOT NULL,
                $COLUMN_AMOUNT REAL,
                $COLUMN_METHOD TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_RECEIPT TEXT
            )
        """.trimIndent()

        db.execSQL(createCategoryTable)
        db.execSQL(createExpenseTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
        onCreate(db)
    }

    fun insertCategory(name: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY_NAME, name)
        }
        val result = db.insert(TABLE_CATEGORY, null, values)
        db.close()
        return result != -1L
    }

    fun getAllCategories(): List<String> {
        val db = readableDatabase
        val list = mutableListOf<String>()
        val cursor = db.rawQuery("SELECT $COLUMN_CATEGORY_NAME FROM $TABLE_CATEGORY", null)
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))
        }
        cursor.close()
        db.close()
        return list
    }

    fun insertExpense(
        name: String,
        amount: Double,
        method: String,
        category: String,
        date: String,
        receipt: String?
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EXPENSE_NAME, name)
            put(COLUMN_AMOUNT, amount)
            put(COLUMN_METHOD, method)
            put(COLUMN_CATEGORY, category)
            put(COLUMN_DATE, date)
            put(COLUMN_RECEIPT, receipt)
        }
        val result = db.insert(TABLE_EXPENSE, null, values)
        db.close()
        return result != -1L
    }
}