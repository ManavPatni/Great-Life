package com.thecodeproject.`in`.greatlife.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.thecodeproject.`in`.greatlife.FoodActivity
import com.thecodeproject.`in`.greatlife.MainActivity
import com.thecodeproject.`in`.greatlife.PhysicalFitnessActivity
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

    private val CHANNEL_ID = "MyChannelID"
    private val notificationId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        askNotificationPermission()

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

        createNotificationChannel()

        //how you feel
        binding.tvAngry.setOnClickListener {
            showSnackBar("ohh noo..Lets make you happy!!")
            sendNotification("Let's make you happy!!","Why don't you try meditation it will help you reduce you anger")
        }
        binding.tvSad.setOnClickListener {
            showSnackBar("ohh noo..Lets make you happy!!")
            sendNotification("Let's make you happy!!","Listen to interesting podcast!!")
        }
        binding.tvNeutral.setOnClickListener {
            showSnackBar("ohh...Lets make you happy!!")
            sendNotification("Let's make you happy!!","Yoga sa hi hoga 😊😊!!")
        }
        binding.tvGood.setOnClickListener {
            showSnackBar("nice...now make you happy!!")
            sendNotification("Let's make you happy!!","Yoga sa hi hoga 😊😊!!")
        }
        binding.tvHappy.setOnClickListener {
            showSnackBar("Wow!! great keep going...")
            sendNotification("Wooooow!!! happy!!","😊😊!!")
        }

        //cv
        binding.cvNutrition.setOnClickListener { startActivity(Intent(requireContext(),FoodActivity::class.java)) }
        binding.cvSleepSound.setOnClickListener { openInChrome("https://shuteye.ai/relaxing-sounds/") }
        binding.cvYoga.setOnClickListener { startActivity(Intent(requireContext(), PhysicalFitnessActivity::class.java)) }

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

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                //Permission granted
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                //nothing
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK can post notifications.
        } else {
            //Inform user that that your app will not show notifications.
            println("Failed")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannelName"
            val descriptionText = "My notification channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
               context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_happy)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }
}