package com.example.homework21.presentation.extension

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.homework21.R

fun AppCompatImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this);
}