package com.intive.patronage.retro.main.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.intive.patronage.retro.R
import com.intive.patronage.retro.common.constants.Constants
import com.intive.patronage.retro.common.network.CheckNetworkConnect
import com.intive.patronage.retro.databinding.ActivityMainBinding
import com.intive.patronage.retro.databinding.HeaderNavigationDrawerBinding
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import com.intive.patronage.retro.offline.presentation.view.OfflineActivity
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val checkNet: CheckNetworkConnect by inject()
    lateinit var binding: ActivityMainBinding
    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        initBottomBarAndDrawer()
        callNetworkConnection()
        signInResultLauncher = registerForActivityResult(viewModel.getActivityResultContract()) { res ->
            viewModel.onResult(res)
        }
        auth.currentUser?.getIdToken(false)?.addOnSuccessListener { Constants.token = it.token }
        userAuth(splashScreen)
    }

    private fun updateDrawerHeaderInfo(user: FirebaseUser?) {
        val bindingHeader: HeaderNavigationDrawerBinding = HeaderNavigationDrawerBinding.bind(binding.navView.getHeaderView(0))

        Picasso.with(this)
            .load(user?.photoUrl)
            .placeholder(R.drawable.ic_avatar_default)
            .into(bindingHeader.avatar)
        bindingHeader.headerTextName.text = user?.displayName
        bindingHeader.headerTextEmail.text = user?.email
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun initBottomBarAndDrawer() {
        val drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottomAppBar: BottomAppBar = binding.bottomAppBar
        val appConfig = AppBarConfiguration(
            setOf(
                R.id.historyFragment,
                R.id.boardsFragment,
                R.id.profileFragment,
                R.id.moreFragment
            ),
            drawerLayout
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomAppBar, navController, appConfig)
        NavigationUI.setupWithNavController(navView, navController)
    }

    private fun userAuth(splashScreen: SplashScreen) {
        if (!viewModel.isLogged()) {
            splashScreen.setKeepOnScreenCondition { !viewModel.isReady() }
            signIn()
        } else {
            splashScreen.setKeepOnScreenCondition { false }
        }
    }

    fun signIn() {
        signInResultLauncher.launch(viewModel.getIntent())
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isBackPressed()) {
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
            if (!isConnected) {
                goToOfflineScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.hasNoNetwork()) {
            goToOfflineScreen()
        }
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateDrawerHeaderInfo(currentUser)
        }
    }

    private fun goToOfflineScreen() {
        val intent = Intent(this, OfflineActivity::class.java)
        startActivity(intent)
    }
}
