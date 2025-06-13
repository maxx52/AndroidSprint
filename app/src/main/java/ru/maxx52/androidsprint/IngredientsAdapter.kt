package ru.maxx52.androidsprint

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.entities.Ingredient

class IngredientsAdapter(
    private var dataSet: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var quantity: Double = 1.0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.tvIngredientName)
        val quantityTextView: TextView = view.findViewById(R.id.tvIngredientCount)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredients, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = dataSet[position]
        val originalQuantity = ingredient.quantity.toDoubleOrNull() ?: 0.0
        val updatedQuantity = originalQuantity * quantity
        val displayQuantity = if (updatedQuantity % 1.0 == 0.0) {
            updatedQuantity.toInt().toString()
        } else {
            String.format("%.1f", updatedQuantity)
        }
        val displayText = "$displayQuantity ${ingredient.unitOfMeasure}"
        viewHolder.titleTextView.text = ingredient.description
        viewHolder.quantityTextView.text = displayText
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(quantity: Double) {
        this.quantity = quantity
        dataSet.also { this.dataSet = it }
        notifyDataSetChanged()
    }
}