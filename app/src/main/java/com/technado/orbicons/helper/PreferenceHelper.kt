package com.technado.orbicons.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technado.orbicons.model.AppModel
import java.lang.reflect.Type

open class PreferenceHelper {
    val USER = "app_model"
    val FILENAME = "app_preferences"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var mPrefs: SharedPreferences

    public fun putBooleanPreference(
        context: Context, prefsName: String,
        key: String, value: Boolean
    ) {
        val preferences = context.getSharedPreferences(
            prefsName,
            Activity.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    public fun getBooleanPreference(
        context: Context, prefsName: String,
        key: String
    ): Boolean {
        val preferences = context.getSharedPreferences(
            prefsName,
            Activity.MODE_PRIVATE
        )
        return preferences.getBoolean(key, false)
    }

    fun putStringPreference(
        context: Context, prefsName: String,
        key: String, value: String
    ) {
        val preferences = context.getSharedPreferences(
            prefsName,
            Activity.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    companion object {
        fun setAllAppsLocal(list: ArrayList<AppModel>, context: Context): Boolean {
            val prefs = context.getSharedPreferences("prefsName", Activity.MODE_PRIVATE)
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(list)
            editor.putString("apps", json)
            editor.apply()
            return true
        }

        fun getAllAppsLocal(context: Context): ArrayList<AppModel> {
            val prefs = context.getSharedPreferences("prefsName", Activity.MODE_PRIVATE)
            val json = prefs.getString("apps", null)
            val type = object : TypeToken<ArrayList<AppModel>>() {}.type
            return Gson().fromJson(json, type)
        }
    }

    protected fun getStringPreference(
        context: Context,
        prefsName: String,
        key: String
    ): String {
        val preferences = context.getSharedPreferences(
            prefsName,
            Activity.MODE_PRIVATE
        )
        return preferences.getString(key, "")!!
    }

    open fun <T> setApps(list: List<T>?, context: Context) {
        sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        val gson = Gson()
        val json = gson.toJson(list)

        val editor = sharedPreferences.edit()
        editor.putString("apps", json)
        editor.apply()
    }

    open fun getApps(context: Context): ArrayList<AppModel> {
        val arrayItems: ArrayList<AppModel>
        sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        val serializedObject = sharedPreferences.getString("apps", null)
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<AppModel?>?>() {}.type
        arrayItems = gson.fromJson<ArrayList<AppModel>>(serializedObject, type)
        return arrayItems
    }
}