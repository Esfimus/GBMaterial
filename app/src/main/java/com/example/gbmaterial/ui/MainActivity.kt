package com.example.gbmaterial.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gbmaterial.R
import com.example.gbmaterial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var ui: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        runMainFragment()
    }

    private fun runMainFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance())
            .commit()
    }
}