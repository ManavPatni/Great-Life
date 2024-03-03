package com.thecodeproject.`in`.greatlife.account

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thecodeproject.`in`.greatlife.MainActivity
import com.thecodeproject.`in`.greatlife.R
import com.thecodeproject.`in`.greatlife.databinding.ActivitySigninBinding
import com.thecodeproject.`in`.greatlife.sharedPrefs.SignInSharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SigninActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySigninBinding

    //firebase auth
    private lateinit var auth: FirebaseAuth

    //google auth
    private lateinit var googleSignInClient: GoogleSignInClient

    //shared pref
    private lateinit var signInSharedPreferencesHelper: SignInSharedPreferenceHelper

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signInSharedPreferencesHelper = SignInSharedPreferenceHelper(this)

        //firebase auth
        auth = FirebaseAuth.getInstance()

        //google auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //initialize progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)

        binding.btnStart.setOnClickListener { signInGoogle() }

    }

    private fun signInGoogle() {
        progressDialog.show()
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    // Inside handleResults function
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            account?.let { updateUI(it) }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Save status and user type in SharedPreferences
                signInSharedPreferencesHelper.setSignInStatus("completed")
                signInSharedPreferencesHelper.setUid(auth.uid!!)
                exit()
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun exit() {
        progressDialog.dismiss()
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}