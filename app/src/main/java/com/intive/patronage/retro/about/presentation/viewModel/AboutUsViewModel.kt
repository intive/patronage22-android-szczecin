package com.intive.patronage.retro.about.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.intive.patronage.retro.about.model.entity.DevsRemote
import com.intive.patronage.retro.about.model.repo.Storage
import com.intive.patronage.retro.about.presentation.entity.Dev
import com.intive.patronage.retro.about.presentation.entity.Devs

class AboutUsViewModel(private val storage: Storage) : ViewModel() {
    private val devsPath = "about-us.json"
    private var devs = Devs()

    fun downloadDevs(): MutableLiveData<Boolean> {
        val areDevsLoaded = MutableLiveData<Boolean>()
        val oneMegabyte: Long = 1024 * 1024

        storage.getFile(devsPath).getBytes(oneMegabyte).addOnSuccessListener { devsFile ->
            devs = Devs(
                (
                    Gson().fromJson(devsFile.decodeToString(), DevsRemote::class.java).devs.map
                    { devRemote ->
                        Dev(Uri.parse(devRemote.avatarUrl), devRemote.displayName, devRemote.email, devRemote.githubUrl, devRemote.role)
                    }
                    )
            )
            areDevsLoaded.postValue(true)
        }.addOnFailureListener {
            areDevsLoaded.postValue(false)
        }
        return areDevsLoaded
    }

    fun getDevs() = devs
}
