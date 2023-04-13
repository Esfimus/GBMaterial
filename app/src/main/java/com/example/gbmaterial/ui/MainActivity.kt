package com.example.gbmaterial.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.gbmaterial.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var ui: ActivityMainBinding
    private val fragments = listOf(
        NasaApodFragment.newInstance(),
        NasaEpicFragment.newInstance()
    )
    private val fragmentsNames = listOf("APOD", "EPIC")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        initPagerView()
    }

    private fun initPagerView() {
        val pager: ViewPager2 = ui.pager
        pager.adapter = PagerAdapter(this, fragments)
        val tabLayout: TabLayout = ui.tabLayout
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = fragmentsNames[position]
        }.attach()
    }
}