package com.intive.patronage.retro.common.binding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.intive.patronage.retro.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: Uri?) {
    Glide.with(view.context)
        .load(imageUrl)
        .centerCrop()
        .error(R.drawable.ic_avatar_default)
        .into(view)
}
