package com.intive.patronage.retro.main.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import com.intive.patronage.retro.R
import com.intive.patronage.retro.auth.model.service.AuthToken
import com.intive.patronage.retro.common.network.NetworkConnectionObserver
import com.intive.patronage.retro.databinding.ActivityMainBinding
import com.intive.patronage.retro.databinding.HeaderNavigationDrawerBinding
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bindingHeader: HeaderNavigationDrawerBinding
    private val viewModel: MainViewModel by viewModel()
    private val net: NetworkConnectionObserver by inject()
    private val authToken: AuthToken by inject()
    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var changePictureResultLauncher: ActivityResultLauncher<String>
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingHeader = HeaderNavigationDrawerBinding.bind(binding.navView.getHeaderView(0))
        bindingHeader.lifecycleOwner = this
        splashScreen.setKeepOnScreenCondition {
            val isLoaded = viewModel.isUserTokenLoaded(this)
            if (isLoaded) {
                init()
            }
            !isLoaded
        }
        signInResultLauncher = registerForActivityResult(viewModel.getActivityResultContract()) { res ->
            if (res.resultCode != Activity.RESULT_OK) {
                finish()
            } else {
                authToken.startRefresh()
            }
        }
        changePictureResultLauncher = changePictureResult()
        bindingHeader.avatar.setOnClickListener {
            changePictureResultLauncher.launch("image/*")
        }

        userAuth()
        setContentView(binding.root)
        callNetworkConnection()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.getUser() != null) {
            bindingHeader.viewModel = viewModel
        }
    }

    fun signIn() {
        signInResultLauncher.launch(viewModel.getIntent())
    }

    private fun initBottomBarAndDrawer() {
        val navView: NavigationView = binding.navView
        val appConfig = AppBarConfiguration(
            navView.menu,
            binding.drawerLayout
        )

        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        NavigationUI.setupWithNavController(binding.bottomAppBar, navController, appConfig)
        NavigationUI.setupWithNavController(navView, navController)
    }

    private fun userAuth() {
        if (!viewModel.isLogged()) {
            signIn()
            initBottomBarAndDrawer()
        } else {
            authToken.startRefresh()
        }
    }

    private fun callNetworkConnection() {
        net.status.observe(this) { isConnected ->
            if (!isConnected) {
                goToOfflineScreen()
            }
        }
    }

    private fun goToOfflineScreen() {
        binding.navHostFragment.findNavController().navigate(R.id.offlineActivity)
    }

    private fun init() {
        initBottomBarAndDrawer()
    }

    private fun changePictureResult(): ActivityResultLauncher<String> {
        val changePictureResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null && it != viewModel.getPicUri()) {
                bindingHeader.avatar.visibility = View.GONE
                bindingHeader.progressBarCircular.visibility = ProgressBar.VISIBLE

                viewModel.changeProfilePicture(it).observe(this) { isLoaded ->
                    if (isLoaded) {
                        bindingHeader.avatar.visibility = View.VISIBLE
                        bindingHeader.progressBarCircular.visibility = ProgressBar.GONE
                    } else {
                        bindingHeader.avatar.visibility = View.VISIBLE
                        bindingHeader.progressBarCircular.visibility = ProgressBar.GONE
                    }
                    bindingHeader.invalidateAll()
                }
            }
        }
        return changePictureResult
    }
}
