package com.example.travelapp.base

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(activity : Context) {

    val login = "login"
    val myPref = "Main_Pref"
    val sharedPreference: SharedPreferences

    init {
        sharedPreference = activity.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    }

    fun loginStatus(status: Boolean ) {
        sharedPreference.edit().putBoolean(login, status).apply()
    }

    fun getLoginStatus(): Boolean {
        return sharedPreference.getBoolean(login, false)
    }
}