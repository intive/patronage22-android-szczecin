package com.intive.patronage.retro.offline.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.intive.patronage.retro.auth.model.repo.AuthRepository
import com.intive.patronage.retro.auth.model.service.AuthToken
import com.intive.patronage.retro.common.network.CheckNetworkConnect
import com.intive.patronage.retro.databinding.ActivityOfflineBinding
import org.koin.android.ext.android.inject

class OfflineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfflineBinding
    private val checkNet: CheckNetworkConnect by inject()
    private val authToken: AuthToken by inject()
    private val auth: AuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOfflineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreenMode()
        callNetworkConnection()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun callNetworkConnection() {
        checkNet.status.observe(this) { status ->
            if (status) {
                if (auth.getUser() == null) {
                    finish()
                } else {
                    authToken.generate()
                    authToken.getToken().observe(this) { token ->
                        if (token.isNotEmpty()) {
                            finish()
                        }
                    }
                }
            }
        }
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
}
