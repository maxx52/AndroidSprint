package ru.maxx52.androidsprint

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.maxx52.androidsprint.databinding.ItemMethodBinding

class MethodAdapter(private val dataSet: List<String>)
    : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMethodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMethodBinding.inflate(LayoutInflater
            .from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvMethodNumber.text = (position + 1).toString() + "."
        holder.binding.tvMethodItem.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size
}