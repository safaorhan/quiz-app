package com.hoopow.apps.quiz.quiz.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hoopow.apps.quiz.quiz.QuizConfigViewModel
import com.hoopow.apps.quiz.quiz.category.CategoryAdapter.CategoryHolder
import com.hoopow.apps.quiz.quiz.databinding.ItemCategoryBinding

class CategoryAdapter(private val viewModel: QuizConfigViewModel) :
    ListAdapter<Category, CategoryHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return CategoryHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) =
        holder.bind(getItem(position))

    class CategoryHolder(
        private val binding: ItemCategoryBinding,
        private val viewModel: QuizConfigViewModel
    ) : ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem

        }
    }
}