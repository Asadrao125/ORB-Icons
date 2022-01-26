package com.technado.orbicons.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technado.orbicons.model.AppModel

class SharedPref(context: Context) {
    val mSharedPref: SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun read(key: String?, defValue: String?): String? {
        return mSharedPref.getString(key, defValue)
    }

    fun read(key: String?, defValue: Int): Int {
        return mSharedPref.getInt(key, defValue)
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return mSharedPref.getBoolean(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor = mSharedPref.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun write(key: String?, value: Boolean) {
        val prefsEditor = mSharedPref.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }

    fun write(key: String?, value: Int?) {
        val prefsEditor = mSharedPref.edit()
        prefsEditor.putInt(key, value!!).apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun remove(key: String?) {
        val prefsEditor = mSharedPref.edit()
        prefsEditor.remove(key)
    }

    fun setAllAppsLocal(list: ArrayList<AppModel>): ArrayList<AppModel> {
        val prefsEditor = mSharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        prefsEditor.putString("apps", json)
        prefsEditor.apply()
        return getAllAppsLocal()
    }

    fun getAllAppsLocal(): ArrayList<AppModel> {
        val json = mSharedPref.getString("apps", null)
        val type = object : TypeToken<ArrayList<AppModel>>() {}.type
        return Gson().fromJson(json, type)
    }
}