package com.example.finalprojectmovie.image_loader

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.finalprojectmovie.R

class GlideImageLoader : ImageLoader {
    override fun load(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(
                ColorDrawable(ContextCompat.getColor(imageView.context, R.color.bottom_navigation_color))
            )
            .override(imageView.width, imageView.height)
            .into(imageView)
    }

}
