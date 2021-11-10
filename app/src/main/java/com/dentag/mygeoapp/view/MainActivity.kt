package com.dentag.mygeoapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dentag.mygeoapp.databinding.ActivityMainBinding
import com.dentag.mygeoapp.view.map.MapFragment
import com.dentag.mygeoapp.view.placelist.PlaceListFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.getInfoFab.bringToFront()
        binding.getInfoFab.setOnClickListener {
            openPointListFragment()
        }
        savedInstanceState ?: openMapFragment()
    }

    private fun openMapFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, MapFragment())
            .commitAllowingStateLoss()
    }

    private fun openPointListFragment() {
        binding.getInfoFab.isVisible = false
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, PlaceListFragment())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        with(supportFragmentManager) {
            if (backStackEntryCount > 0) {
                binding.getInfoFab.isVisible = true
                popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}