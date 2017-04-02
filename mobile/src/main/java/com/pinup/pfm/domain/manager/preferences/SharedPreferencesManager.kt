package com.pinup.pfm.domain.manager.preferences

import android.content.Context
import android.content.SharedPreferences
import com.pinup.pfm.di.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Wrapper for shared preferences easy access
 */

class SharedPreferencesManager
@Inject constructor(@ApplicationContext context: Context) {

    private object Constants {
        internal val SHARED_PREFERENCES_NAME = "com.pinup.pfm"
        internal val NO_DATA = -1
    }

    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setBooleanPreference(key: String, value: Boolean): Boolean {
        return preferences.edit().putBoolean(key, value).commit()
    }

    fun getBooleanPreference(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getBooleanPreference(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun setStringPreference(key: String, value: String?): Boolean {
        return preferences.edit().putString(key, value).commit()
    }

    fun getStringPreference(key: String): String? {
        return preferences.getString(key, null)
    }

    fun getStringPreference(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun setIntPreference(key: String, value: Int): Boolean {
        return preferences.edit().putInt(key, value).commit()
    }

    fun getIntPreference(key: String): Int {
        return preferences.getInt(key, Constants.NO_DATA)
    }

    fun getIntPreference(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun setLongPreference(key: String, value: Long): Boolean {
        return preferences.edit().putLong(key, value).commit()
    }

    fun getLongPreference(key: String): Long {
        return preferences.getLong(key, Constants.NO_DATA.toLong())
    }

    fun getLongPreference(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    fun clearPreference(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun clearAllPreference() {
        preferences.edit().clear().apply()
    }
}