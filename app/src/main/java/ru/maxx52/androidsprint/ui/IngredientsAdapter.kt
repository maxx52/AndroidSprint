package ru.maxx52.androidsprint.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.databinding.ItemIngredientsBinding
import ru.maxx52.androidsprint.model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(
    private var dataSet: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private var quantity: BigDecimal = BigDecimal.ONE

    inner class ViewHolder(val binding: ItemIngredientsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            val displayQuantity = (ingredient.quantity.toBigDecimalOrNull() ?: BigDecimal.ZERO)
                .multiply(quantity)
                .setScale(1, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()

            val displayText = "$displayQuantity ${ingredient.unitOfMeasure}"
            binding.tvIngredientName.text = ingredient.description
            binding.tvIngredientCount.text = displayText
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientsBinding.inflate(LayoutInflater
            .from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = dataSet[position]
        viewHolder.bind(ingredient)
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(newIngredients: List<Ingredient>, newPortions: BigDecimal) {
        dataSet = newIngredients
        notifyDataSetChanged()
    }
}