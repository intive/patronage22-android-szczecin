package com.intive.patronage.retro.offline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.intive.patronage.retro.databinding.ActivityOfflineBinding
import com.intive.patronage.retro.util.CheckNetworkConnect
import org.koin.android.ext.android.inject

class OfflineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfflineBinding
    private val checkNet: CheckNetworkConnect by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOfflineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreenMode()
        callNetworkConnection()
    }

    private fun callNetworkConnection() {
        checkNet.observe(this) { finish() }
    }

    private fun fullScreenMode() {
        WindowCompat.setDecorFitsSystemWindows(this.window!!, true)
        WindowInsetsControllerCompat(
            this.window!!,
            this.window?.decorView!!
        ).let { controller ->
            controller.systemBarsBehavior = WindowInsetsControllerCompat
                .BEHAVIOR_SHOW_BARS_BY_TOUCH
            controller.hide(WindowInsetsCompat.Type.systemBars())
            this.supportActionBar?.hide()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}