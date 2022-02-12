package com.intive.patronage.retro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.intive.patronage.retro.databinding.ActivityMainBinding
import com.intive.patronage.retro.firebase.FirebaseViewModel
import com.intive.patronage.retro.network.CheckNetworkConnect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {
    private val firebaseViewModel: FirebaseViewModel by viewModel()
    private val viewModel: MainViewModel by viewModel()
    private val checkNet: CheckNetworkConnect by inject()

    private lateinit var binding: ActivityMainBinding
    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callNetworkConnection()
        initBottomBarAndDrawer()
        signInResultLauncher = registerForActivityResult(firebaseViewModel.getActivityResultContract()) {
            res ->
            firebaseViewModel.onResult(res)
        }
        userAuth(splashScreen)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun initBottomBarAndDrawer() {
        val drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottomAppBar: BottomAppBar = binding.bottomAppBar
        val appConfig = AppBarConfiguration(setOf(R.id.historyFragment, R.id.boardsFragment, R.id.profileFragment), drawerLayout)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomAppBar, navController, appConfig)
        NavigationUI.setupWithNavController(navView, navController)
    }

    private fun userAuth(splashScreen: SplashScreen) {
        if (!firebaseViewModel.isLogged()) {
            splashScreen.setKeepOnScreenCondition { !firebaseViewModel.isReady() }
            signIn()
        } else {
            Log.i("VIEWMODEL", "onCreate: ${viewModel.sayHello()}")
            splashScreen.setKeepOnScreenCondition { false }
        }
    }

    fun signIn() {
        signInResultLauncher.launch(firebaseViewModel.getIntent())
    }

    override fun onResume() {
        super.onResume()
        if (firebaseViewModel.isBackPressed()) {
            finish()
        } else {
            binding.drawerLayout.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        binding.drawerLayout.visibility = View.GONE
    }

    private fun callNetworkConnection() {
        checkNet.observe(this) { isConnected ->
            if (isConnected) {
                Toast.makeText(this, "APP ONLINE", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "APP OFFLINE", Toast.LENGTH_LONG).show()
            }
        }
    }
}
