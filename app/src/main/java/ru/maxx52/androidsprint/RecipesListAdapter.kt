package ru.maxx52.androidsprint

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.entities.Recipe

class RecipesListAdapter(private val dataSet: List<Recipe>) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>(){

    var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) { itemClickListener = listener }

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivRecipeItemImage)
        val titleTextView: TextView = view.findViewById(R.id.tvRecipeTitleItem)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        val recipe = dataSet[position]
        viewHolder.titleTextView.text = recipe.title

        try {
            val drawable = Drawable.createFromStream(viewHolder.imageView.context.assets
                .open(recipe.imageUrl), null)
            viewHolder.imageView.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        viewHolder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(recipe.id)
        }
    }

    override fun getItemCount() = dataSet.size
}