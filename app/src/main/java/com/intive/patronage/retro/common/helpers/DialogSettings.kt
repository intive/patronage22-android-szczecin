package com.intive.patronage.retro.common.helpers

import android.app.Dialog
import android.view.WindowManager

@Suppress("DEPRECATION")
fun softKeyboardHandler(dialog: Dialog?) {
    dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}
