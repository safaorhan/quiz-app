package com.hoopow.apps.quiz.quiz.category

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hoopow.apps.quiz.quiz.R.string

@BindingAdapter("bestScore")
fun TextView.bestScore(bestScore: Int) {
    text = resources.getString(string.best_score, bestScore)
}

@BindingAdapter("items", "adapter")
fun <T, VH : ViewHolder> RecyclerView.items(items: List<T>, adapter: ListAdapter<T, VH>) {
    if (this.adapter != adapter) {
        this.adapter = adapter
    }

    adapter.submitList(items)
}

@BindingAdapter("itemDecoration")
fun RecyclerView.itemDecoration(decoration: ItemDecoration) {
    if (itemDecorationCount == 0) {
        addItemDecoration(decoration)
    }
}