package com.intive.patronage.retro

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.intive.patronage.retro.databinding.ActivityMainBinding
import com.intive.patronage.retro.firebase.FirebaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val firebaseViewModel: FirebaseViewModel by viewModel()
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        splashScreen.setKeepOnScreenCondition { false }
        setContentView(binding.root)

        if (!firebaseViewModel.isUserLogged()) {
            firebaseViewModel.signIn(this)
        } else {
            Log.i("VIEWMODEL", "onCreate: ${viewModel.sayHello()}")
        }
    }
}
