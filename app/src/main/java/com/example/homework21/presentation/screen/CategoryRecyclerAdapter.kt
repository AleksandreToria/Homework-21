package com.example.homework21.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework21.databinding.CategoryLayoutBinding

class CategoryRecyclerAdapter(private val includeAllCategory: Boolean = true) :
    ListAdapter<String, CategoryRecyclerAdapter.CategoriesViewHolder>(CategoryDiffUtil()) {

    private var onItemClick: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        this.onItemClick = listener
    }

    companion object {
        const val ALL_CATEGORY = "All"
    }

    inner class CategoriesViewHolder(private val binding: CategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
        val category = if (includeAllCategory && position == 0) ALL_CATEGORY else getItem(if (includeAllCategory) position - 1 else position)
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (includeAllCategory) 1 else 0
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