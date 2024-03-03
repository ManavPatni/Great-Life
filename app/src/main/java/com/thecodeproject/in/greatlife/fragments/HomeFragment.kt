package com.thecodeproject.`in`.greatlife.fragments

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

        return binding.root
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

}