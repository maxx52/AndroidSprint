package ru.maxx52.androidsprint

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.entities.Recipe
import ru.maxx52.androidsprint.databinding.ItemRecipeBinding

class RecipesListAdapter(private val dataSet: List<Recipe>) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {
    var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        holder.binding.tvRecipeTitleItem.text = recipe.title

        try {
            val drawable = Drawable.createFromStream(
                holder.binding.root.context.assets.open(recipe.imageUrl),
                null
            )
            holder.binding.ivRecipeItemImage.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.binding.root.setOnClickListener {
            itemClickListener?.onItemClick(recipe.id)
        }
    }

    override fun getItemCount() = dataSet.size
}