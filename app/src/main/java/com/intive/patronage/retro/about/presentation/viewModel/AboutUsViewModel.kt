package com.intive.patronage.retro.about.presentation.viewModel

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
            devs = Gson().fromJson(devsFile.decodeToString(), DevsRemote::class.java).mapToDevs()
            areDevsLoaded.postValue(true)
        }.addOnFailureListener {
            areDevsLoaded.postValue(false)
        }
        return areDevsLoaded
    }

    fun getDevs() = devs

    private fun DevsRemote.mapToDevs(): Devs {
        val developers = Devs()
        for (dev in devs) {
            developers.devs.add(
                Dev(
                    avatarUrl = dev.avatarUrl,
                    displayName = dev.displayName,
                    email = dev.email,
                    githubUrl = dev.githubUrl,
                    role = dev.role
                )
            )
        }
        return developers
    }
}
