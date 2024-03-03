package com.thecodeproject.`in`.greatlife.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.thecodeproject.`in`.greatlife.R
import com.thecodeproject.`in`.greatlife.databinding.FragmentHomeBinding
import com.thecodeproject.`in`.greatlife.sharedPrefs.SignInSharedPreferenceHelper

class HomeFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentHomeBinding

    //firebase auth
    private lateinit var auth: FirebaseAuth

    //sign in pref
    private lateinit var signInPrefs: SignInSharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //Sign In
        signInPrefs = SignInSharedPreferenceHelper(requireContext())

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

        //mental wellbeing
        binding.cvMentalHealth1.setOnClickListener { openInChrome("https://content.production.cdn.art19.com/validation=1709565622,b58b4931-0eb1-56ac-854e-34675ea969a4,luXiJXA66xSS7u1Cf0oBsJbf3ik/episodes/e8f42c98-a3f0-4e37-b19c-e0daf790c08b/16a3850a3079b62239d2dbadc99ae104ad80404652a59d7d7f527ef971b8b0d6da6678b80ffbeb730cf36cb0642ad3cafa3220d633ccfe5fce0975ca5e0698ce/%23685%20Dr%20Devika%20Bhushan.mp3") }

        return binding.root
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun openInChrome(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}