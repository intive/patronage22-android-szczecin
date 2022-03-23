package com.intive.patronage.retro.main.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.navigation.NavigationView
import com.intive.patronage.retro.R
import com.intive.patronage.retro.auth.model.service.AuthToken
import com.intive.patronage.retro.common.network.CheckNetworkConnect
import com.intive.patronage.retro.databinding.ActivityMainBinding
import com.intive.patronage.retro.databinding.HeaderNavigationDrawerBinding
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bindingHeader: HeaderNavigationDrawerBinding
    private val viewModel: MainViewModel by viewModel()
    private val checkNet: CheckNetworkConnect by inject()
    private val authToken: AuthToken by inject()
    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>
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
        val drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottomAppBar: BottomAppBar = binding.bottomAppBar
        val appConfig = AppBarConfiguration(
            navView.menu,
            drawerLayout
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomAppBar, navController, appConfig)
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
        checkNet.status.observe(this) { isConnected ->
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
}
