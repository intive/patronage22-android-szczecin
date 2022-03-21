package com.intive.patronage.retro.common.binding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: Uri?) {
    Picasso.with(view.context)
        .load(imageUrl)
        .placeholder(com.intive.patronage.retro.R.drawable.ic_avatar_default)
        .into(view)
}
