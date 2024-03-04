package com.thecodeproject.`in`.greatlife

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thecodeproject.`in`.greatlife.account.SigninActivity
import com.thecodeproject.`in`.greatlife.sharedPrefs.SignInSharedPreferenceHelper

class SplashScreen : AppCompatActivity() {

    //splash time
    private val Splash_Time_Out = 1000

    //shared pref
    private lateinit var loginHelper: SignInSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Initialize SharedPreferencesHelper
        loginHelper = SignInSharedPreferenceHelper(this)

        // If onboarding status is null, show the OnBoarding
        if (loginHelper.getSignInStatus() == "completed") {
            splashOver()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, SigninActivity::class.java))
                //startActivity(Intent(this, FoodActivity::class.java))
                overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
                finishAffinity()
            }, Splash_Time_Out.toLong())
        }

    }

    private fun splashOver() {
        // Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
            finishAffinity()
        }, Splash_Time_Out.toLong())
    }

}