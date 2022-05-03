package com.intive.patronage.retro.retro.presentation.view

import android.content.Context
import com.google.android.material.snackbar.Snackbar

class RetroNavigator {

    var fragment: RetroFragment? = null

    fun attach(fragment: RetroFragment) {
        this.fragment = fragment
    }

    fun detach(fragment: RetroFragment) {
        if (this.fragment === fragment)
            this.fragment = null
    }

    fun getContextFromBoardFragment(): Context? = fragment!!.context

    fun errorSnackBar(message: Int) {
        Snackbar.make(fragment!!.requireView(), fragment!!.getString(message), Snackbar.LENGTH_SHORT).show()
    }
}
