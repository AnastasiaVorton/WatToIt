package com.example.wattoit.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.example.wattoit.R

fun ImageView.downloadImage(url: String?) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
