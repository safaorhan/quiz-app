package com.hoopow.apps.quiz.home

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("highScore")
fun TextView.highScore(highScore: Int?) {
    val scoreText = highScore?.toString() ?: resources.getString(R.string.not_available)
    text = resources.getString(R.string.high_score, scoreText)
}