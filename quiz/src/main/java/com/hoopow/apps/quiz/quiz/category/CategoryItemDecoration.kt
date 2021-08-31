package com.hoopow.apps.quiz.quiz.category

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import com.hoopow.apps.quiz.quiz.R.dimen
import javax.inject.Inject

class CategoryItemDecoration @Inject constructor() : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val spacing = parent.resources.getDimensionPixelSize(dimen.category_item_spacing)

        val isFirstItem = parent.getChildAdapterPosition(view) == 0
        val isLastItem = parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1

        when {
            isFirstItem -> {
                outRect.left = spacing
                outRect.right = spacing / 2
            }
            isLastItem -> {
                outRect.left = spacing / 2
                outRect.right = spacing
            }
            else -> {
                outRect.left = spacing / 2
                outRect.right = spacing / 2
            }
        }
    }
}