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
        // Create Categories table
        val createCategoriesTable = """
            CREATE TABLE $TABLE_CATEGORIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY_NAME TEXT UNIQUE
            )
        """.trimIndent()

        db.execSQL(createCategoriesTable)

        // Create Expenses table with a foreign key reference to Categories
        val createExpensesTable = """
            CREATE TABLE $TABLE_EXPENSES (
                $EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $EXPENSE_NAME TEXT NOT NULL,
                $EXPENSE_AMOUNT REAL NOT NULL,
                $EXPENSE_PAYMENT_METHOD TEXT NOT NULL,
                $EXPENSE_CATEGORY INTEGER NOT NULL,
                $EXPENSE_DATE TEXT NOT NULL,
                FOREIGN KEY ($EXPENSE_CATEGORY) REFERENCES $TABLE_CATEGORIES($COLUMN_ID)
            )
        """.trimIndent()

        db.execSQL(createExpensesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop old tables if they exist and create fresh ones
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        onCreate(db)
    }
    fun deleteCategory(category: String): Boolean {
        val db = this.writableDatabase
        val categoryId = getCategoryId(category)  // Get category ID for the category name

        if (categoryId == -1) return false  // Category doesn't exist

        val rowsDeleted = db.delete(
            TABLE_CATEGORIES,
            "$COLUMN_CATEGORY_NAME = ?",
            arrayOf(category)
        )

        db.close()
        return rowsDeleted > 0
    }

    fun insertCategory(category: String): Boolean {
        val db = this.writableDatabase

        if (category.isBlank()) return false

        val contentValues = ContentValues().apply {
            put(COLUMN_CATEGORY_NAME, category.trim()) // Keeping case-sensitive
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
        return latestExpense
    }

    fun getAllExpenses(): List<ExpenseEntry> {
        val db = readableDatabase
        val list = mutableListOf<ExpenseEntry>()
        val cursor = db.rawQuery("SELECT $EXPENSE_NAME, $EXPENSE_AMOUNT, $EXPENSE_DATE FROM $TABLE_EXPENSES ORDER BY $EXPENSE_ID DESC", null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val amount = cursor.getDouble(1)
            val date = cursor.getString(2)
            list.add(ExpenseEntry(name, amount, date))
        }

        cursor.close()
        return list
    }

    data class ExpenseEntry(val name: String, val amount: Double, val date: String)

    fun insertExpense(name: String, amount: Double, paymentMethod: String, category: String, date: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(EXPENSE_NAME, name)
            put(EXPENSE_AMOUNT, amount)
            put(EXPENSE_PAYMENT_METHOD, paymentMethod)
            put(EXPENSE_CATEGORY, getCategoryId(category))  // Use ID instead of name
            put(EXPENSE_DATE, date)
        }
        db.insert(TABLE_EXPENSES, null, contentValues)
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
        return total
    }

    // Helper function to get category ID
    private fun getCategoryId(category: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_ID FROM $TABLE_CATEGORIES WHERE $COLUMN_CATEGORY_NAME = ?",
            arrayOf(category)
        )
        var categoryId = -1
        if (cursor.moveToFirst()) {
            categoryId = cursor.getInt(0)
        }
        cursor.close()
        return categoryId
    }
}
