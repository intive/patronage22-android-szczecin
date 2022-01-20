package com.intive.patronage.retro

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("VIEWMODEL", "onCreate: ${viewModel.sayHello()}")
    }
}
