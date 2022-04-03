package com.intive.patronage.retro.about.model.repo

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Storage {
    private val storageRef = Firebase.storage.reference

    fun getFile(pathString: String) = storageRef.child(pathString)
}
