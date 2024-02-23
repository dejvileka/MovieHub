package com.dejvidleka.moviehub.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val PREF_NAME = "my_app_preferences"
    private const val KEY_SELECTED_REGION_CODE = "selected_region_code"
    private lateinit var preferences: SharedPreferences
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveRegionCode(context: Context, isoCode: String) {
        preferences.edit().putString(KEY_SELECTED_REGION_CODE, isoCode).apply()
    }

    fun getRegionCode(context: Context): String? {
        return preferences.getString(KEY_SELECTED_REGION_CODE, null)

    }

}