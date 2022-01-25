package com.intive.patronage.retro

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.retro.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("VIEWMODEL", "onCreate: ${viewModel.sayHello()}")
    }
}
