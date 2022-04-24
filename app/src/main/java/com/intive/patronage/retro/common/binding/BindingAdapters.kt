package com.intive.patronage.retro.common.binding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.intive.patronage.retro.R

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: Uri?) {
    Glide.with(this)
        .load(imageUrl)
        .centerCrop()
        .error(R.drawable.ic_avatar_default)
        .into(this)
}
