package ru.maxx52.androidsprint

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.entities.Ingredient
import java.math.BigDecimal

class IngredientsAdapter(
    private var dataSet: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var quantity: BigDecimal = BigDecimal.ONE

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
        val originalQuantity = ingredient.quantity.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val updatedQuantity = originalQuantity.multiply(quantity)
        val displayQuantity = when {
            updatedQuantity.remainder(BigDecimal.ONE) == BigDecimal.ZERO ->
                updatedQuantity.toInt().toString()
            else -> "%.1f".format(updatedQuantity)
        }
        val displayText = "$displayQuantity ${ingredient.unitOfMeasure}"
        viewHolder.titleTextView.text = ingredient.description
        viewHolder.quantityTextView.text = displayText
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(quantity: BigDecimal) {
        this.quantity = quantity
        notifyDataSetChanged()
    }
}