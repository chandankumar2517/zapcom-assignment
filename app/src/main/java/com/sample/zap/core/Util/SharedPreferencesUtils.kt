package com.sample.zap.core.Util

import android.content.Context

object SharedPreferencesUtils  {

        // Save user data into SharedPreferences
        private const val PREF_NAME = "app_prefs"
        // Helper to provide SharedPreferences instance
        fun saveUserData(context: Context,  username: String?, token: String?, userID : Int?) {
            val sharedPreferences =  context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString("username", username)
                putString("token", token)
                putString("userID", userID.toString())
                apply() // Asynchronously saves the data
            }
    }

    // Retrieve user data from SharedPreferences
    fun getUserData(context: Context): Triple<String?, String?, String?> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        val token = sharedPreferences.getString("token", null)
        val userID = sharedPreferences.getString("userID", null)
        return Triple(username, token,userID.toString())
    }

    // Optionally, clear user data from SharedPreferences
    fun clearUserData(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}
