package ru.maxx52.androidsprint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.entities.Ingredient

class IngredientsAdapter(private val dataSet: List<Ingredient>)
    : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

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

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = dataSet[position]
        viewHolder.titleTextView.text = ingredient.description
        viewHolder.quantityTextView.text = ingredient.quantity
        viewHolder.unitOfMeasureTextView.text = ingredient.unitOfMeasure
    }

    override fun getItemCount() = dataSet.size
}