package com.intive.patronage.retro.about.presentation.entity

import android.net.Uri

data class Dev(
    val avatarUrl: Uri,
    val displayName: String,
    val email: String,
    val githubUrl: String,
    val role: String,
)
