package com.example.homework21.presentation.screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework21.databinding.ItemBinding
import com.example.homework21.presentation.extension.loadImage
import com.example.homework21.presentation.model.Items

class HomeRecyclerAdapter : ListAdapter<Items, HomeRecyclerAdapter.ViewHolder>(ItemDiffUtil()) {

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Items

        fun bind() {
            model = currentList[adapterPosition]

            binding.apply {
                image.loadImage(model.cover)
                title.text = model.title
                price.text = model.price

                if (!model.favorite) {
                    heart.visibility = View.GONE
                    background.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    class ItemDiffUtil : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }
    }
}