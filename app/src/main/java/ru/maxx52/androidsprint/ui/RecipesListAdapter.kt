package ru.maxx52.androidsprint.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.data.IMAGE_BASE_URL
import ru.maxx52.androidsprint.model.Recipe
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
            val completeImageUrl = "$IMAGE_BASE_URL${recipe.imageUrl}"
            val imageView = holder.binding.ivRecipeItemImage
            Glide.with(imageView.context)
                .load(completeImageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.binding.root.setOnClickListener {
            itemClickListener?.onItemClick(recipe.id)
        }
    }

    override fun getItemCount() = dataSet.size
}