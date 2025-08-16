package ru.maxx52.androidsprint.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.databinding.ItemIngredientsBinding
import ru.maxx52.androidsprint.model.Ingredient

class IngredientsAdapter : ListAdapter<Ingredient, IngredientsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean =
                oldItem.description == newItem.description

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean =
                oldItem == newItem
        }
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
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size
}