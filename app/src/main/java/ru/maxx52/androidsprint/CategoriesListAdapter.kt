package ru.maxx52.androidsprint

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.entities.Category
import java.io.IOException

class CategoriesListAdapter(private val dataSet: List<Category>)
    : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivCategory)
        val tvCategoryTitle: TextView = view.findViewById(R.id.tvCategoryTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvCategoryDescription)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_category, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = dataSet[position]
        holder.tvCategoryTitle.text = category.title
        holder.tvDescription.text = category.description

        try {
            val inputStream = holder.itemView.context.assets.open(category.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.imageView.setImageDrawable(drawable)
            inputStream.close()
        } catch (e: IOException) {
            Log.e("CategoriesListAdapter", "Ошибка загрузки картинок из assets", e)
        }
    }


    override fun getItemCount(): Int = dataSet.size
}