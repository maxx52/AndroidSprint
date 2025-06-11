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

    private var displayedDataSet: List<Ingredient> = dataSet
    private var quantity: Double = 1.0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.tvIngredientName)
        val quantityTextView: TextView = view.findViewById(R.id.tvIngredientCount)
        val unitOfMeasureTextView: TextView = view.findViewById(R.id.tvUnitOfMeasure)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredients, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = dataSet[position]
        viewHolder.titleTextView.text = ingredient.description
        viewHolder.quantityTextView.text = ingredient.quantity
        viewHolder.quantityTextView.text = ingredient.unitOfMeasure
    }

    override fun getItemCount() = displayedDataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(quantityMultiplier: Double, progress: Int? = null) {
        quantity = quantityMultiplier
        displayedDataSet = if (progress != null && progress in 1..dataSet.size) {
            dataSet.take(progress)
        } else {
            dataSet
        }
        notifyDataSetChanged()
    }
}