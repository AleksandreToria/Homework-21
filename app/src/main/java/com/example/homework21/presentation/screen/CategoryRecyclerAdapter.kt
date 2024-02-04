package com.example.homework21.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework21.databinding.CategoryLayoutBinding

class CategoryRecyclerAdapter :
    ListAdapter<String, CategoryRecyclerAdapter.CategoriesViewHolder>(CategoryDiffUtil()) {

    inner class CategoriesViewHolder(private val binding: CategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var onItemClick: ((String) -> Unit)? = null

        fun setOnItemClickListener(listener: (String) -> Unit) {
            this.onItemClick = listener
        }

        fun bind(text: String) {
            with(binding) {
                category.text = text

                category.setOnClickListener {
                    onItemClick?.invoke(text)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoriesViewHolder(
        CategoryLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    class CategoryDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}