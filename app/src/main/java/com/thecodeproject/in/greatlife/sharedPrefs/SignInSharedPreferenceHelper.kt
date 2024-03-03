package com.thecodeproject.`in`.greatlife.sharedPrefs

import android.content.Context
import android.content.SharedPreferences

class SignInSharedPreferenceHelper(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)

    fun setSignInStatus(status: String) {
        sharedPreferences.edit().putString("Status", status).apply()
    }

    fun setUid(uid: String) {
        sharedPreferences.edit().putString("Uid", uid).apply()
    }

    fun getSignInStatus(): String? {
        return sharedPreferences.getString("Status", "pending")
    }

    fun getUid(): String? {
        return sharedPreferences.getString("Uid", "none")
    }
}