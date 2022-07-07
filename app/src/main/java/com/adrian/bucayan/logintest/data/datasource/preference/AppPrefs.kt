package com.adrian.bucayan.logintest.data.datasource.preference

import android.content.Context
import android.content.SharedPreferences

class AppPrefs(val context: Context) {

    companion object {
        private const val PREFS_FILENAME = "app_preferences"
        const val KEY_IS_LOGIN = "is_login"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var isLogin:  Boolean
        get() = sharedPrefs.getBoolean(KEY_IS_LOGIN, false)
        set(value) = with(sharedPrefs.edit()) {
            putBoolean(KEY_IS_LOGIN, value)
            apply()
        }
}
