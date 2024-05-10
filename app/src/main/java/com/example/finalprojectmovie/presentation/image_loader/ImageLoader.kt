package com.example.finalprojectmovie.image_loader

import android.widget.ImageView

interface ImageLoader {
    fun load(imageView: ImageView, url: String)
}
