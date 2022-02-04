package com.intive.patronage.retro

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.intive.patronage.retro.databinding.ActivityMainBinding
import com.intive.patronage.retro.firebase.FirebaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val firebaseViewModel: FirebaseViewModel by viewModel()
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    lateinit var signInResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomBarAndDrawer()
        signInResultLauncher = firebaseViewModel.getSignInResultLauncher(this)
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
        val appConfig = AppBarConfiguration(setOf(R.id.historyFragment, R.id.boardsFragment), drawerLayout)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomAppBar, navController, appConfig)
        NavigationUI.setupWithNavController(navView, navController)
    }

    private fun userAuth(splashScreen: SplashScreen) {
        if (!firebaseViewModel.isUserLogged()) {
            signIn()
            splashScreen.setKeepOnScreenCondition { !firebaseViewModel.isSignInActivityReady() }
        } else {
            Log.i("VIEWMODEL", "onCreate: ${viewModel.sayHello()}")
            splashScreen.setKeepOnScreenCondition { false }
        }
    }

    private fun signIn() {
        val signInIntent = firebaseViewModel.getSignInIntent()
        signInResultLauncher.launch(signInIntent)
    }
}
