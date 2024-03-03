package com.thecodeproject.`in`.greatlife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.thecodeproject.`in`.greatlife.databinding.ActivityMainBinding
import com.thecodeproject.`in`.greatlife.sharedPrefs.SignInSharedPreferenceHelper

class MainActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityMainBinding

    //firebase auth
    private lateinit var auth: FirebaseAuth

    //sign in pref
    private lateinit var signInPrefs: SignInSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Sign In
        signInPrefs = SignInSharedPreferenceHelper(this)

        //Firebase auth
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!
        binding.tvUserName.text = currentUser.displayName
        val profilePicUrl = currentUser.photoUrl

        // Using Glide to load the profile picture and apply the CircleCrop transformation
        Glide.with(this)
            .load(profilePicUrl)
            .transform(CircleCrop())
            //.placeholder(R.drawable.ic_guest) // Placeholder image while loading
            //.error(R.drawable.ic_guest) // Image to display in case of error
            .into(binding.ivUserPic)


        //how you feel
        binding.tvAngry.setOnClickListener { showSnackBar("ohh noo..Lets make you happy!!") }
        binding.tvSad.setOnClickListener { showSnackBar("ohh noo..Lets make you happy!!") }
        binding.tvNeutral.setOnClickListener { showSnackBar("ohh...Lets make you happy!!") }
        binding.tvGood.setOnClickListener { showSnackBar("nice...now make you happy!!") }
        binding.tvHappy.setOnClickListener { showSnackBar("Wow!! great keep going...") }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

}