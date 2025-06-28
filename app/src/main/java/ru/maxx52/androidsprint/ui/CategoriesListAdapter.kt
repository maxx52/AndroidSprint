package ru.maxx52.androidsprint.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.model.Category

class CategoriesListAdapter(private val dataSet: List<Category>):
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) { itemClickListener = listener }

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivCategoryImage)
        val titleTextView: TextView = view.findViewById(R.id.tvTitleCategory)
        val descriptionTextView: TextView = view.findViewById(R.id.tvCategoryDescription)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        viewHolder.titleTextView.text = category.title
        viewHolder.descriptionTextView.text = category.description

        try {
            val drawable = Drawable.createFromStream(viewHolder.imageView.context.assets
                .open(category.imageUrl), null)
            viewHolder.imageView.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        viewHolder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(category.id)
        }
    }

    override fun getItemCount() = dataSet.size
}