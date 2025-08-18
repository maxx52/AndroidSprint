package ru.maxx52.androidsprint.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.databinding.ItemIngredientsBinding
import ru.maxx52.androidsprint.model.Ingredient

class IngredientsAdapter(
    private val onChangeIngredients: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    var dataSet: MutableList<Ingredient> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: ItemIngredientsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            val displayText = "${ingredient.quantity} ${ingredient.unitOfMeasure}"
            binding.tvIngredientName.text = ingredient.description
            binding.tvIngredientCount.text = displayText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    fun triggerChangeIngredients(newPortions: Int) {
        onChangeIngredients?.invoke(newPortions)
    }
}