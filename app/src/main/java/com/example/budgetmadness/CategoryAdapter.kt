package com.example.budgetmadness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class CategoryAdapter(
    context: Context,
    private val categories: MutableList<String>,
    private val dbHelper: BudgetDatabaseHelper
) : ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)

        val categoryTextView = view.findViewById<TextView>(android.R.id.text1)
        categoryTextView.text = categories[position]

        val deleteButton = Button(context).apply {
            text = "Delete"
            setOnClickListener {
                val categoryToDelete = categories[position]
                val success = dbHelper.deleteCategory(categoryToDelete)
                if (success) {
                    categories.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Category deleted!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to delete category", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val layout = view as ViewGroup
        layout.addView(deleteButton)  // Add the delete button to each category item

        return layout
    }
}
