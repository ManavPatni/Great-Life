package com.thecodeproject.`in`.greatlife

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.thecodeproject.`in`.greatlife.databinding.ActivityMainBinding
import com.thecodeproject.`in`.greatlife.fragments.DigitalWellBeingFragment
import com.thecodeproject.`in`.greatlife.fragments.HomeFragment
import com.thecodeproject.`in`.greatlife.sharedPrefs.SignInSharedPreferenceHelper

class MainActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.toString()) {
                "Home" -> {
                    replaceFragment(HomeFragment())
                }
                "Digital WellBeing" -> {
                    replaceFragment(DigitalWellBeingFragment())
                }
                "Communities" -> {
                    //startActivity(Intent(this,PhysicalFitnessActivity::class.java))
                    startActivity(Intent(this,FoodActivity::class.java))
                }}
            return@setOnItemSelectedListener true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}