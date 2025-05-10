package com.example.budgetmadness

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BudgetDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "budget.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CATEGORIES = "Categories"
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORY_NAME = "name"

        const val TABLE_EXPENSES = "expenses"
        const val EXPENSE_ID = "_id"
        const val EXPENSE_NAME = "name"
        const val EXPENSE_AMOUNT = "amount"
        const val EXPENSE_PAYMENT_METHOD = "payment_method"
        const val EXPENSE_CATEGORY = "category"
        const val EXPENSE_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createCategoriesTable = """
            CREATE TABLE $TABLE_CATEGORIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY_NAME TEXT UNIQUE
            )
        """.trimIndent()

        db.execSQL(createCategoriesTable)

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

        if (category.isBlank()) return false

        val contentValues = ContentValues().apply {
            put(COLUMN_CATEGORY_NAME, category.trim().lowercase()) // Normalize
        }

        return try {
            val result = db.insertOrThrow(TABLE_CATEGORIES, null, contentValues)
            result != -1L
        } catch (e: SQLiteConstraintException) {
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllCategories(): List<String> {
        val categoryList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_CATEGORY_NAME FROM $TABLE_CATEGORIES", null)

        if (cursor.moveToFirst()) {
            do {
                categoryList.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return categoryList
    }
    fun getLatestExpense(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $EXPENSE_AMOUNT FROM $TABLE_EXPENSES ORDER BY $EXPENSE_ID DESC LIMIT 1",
            null
        )
        var latestExpense = 0.0
        if (cursor.moveToFirst()) {
            latestExpense = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return latestExpense
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
    fun getTotalExpenses(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($EXPENSE_AMOUNT) FROM $TABLE_EXPENSES", null)
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return total
    }

}
