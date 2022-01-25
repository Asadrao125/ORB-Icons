package com.technado.orbicons.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.adapter.AppsAdapter
import com.technado.orbicons.databinding.HomeFragmentBinding
import com.technado.orbicons.helper.Titlebar
import com.technado.orbicons.model.AppModel

class HomeFragment : BaseFragment() {
    var binding: HomeFragmentBinding? = null
    lateinit var recyclerView: RecyclerView
    private lateinit var installedAppsList: ArrayList<AppModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        getActivityContext()?.unlockMenu()

        recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = GridLayoutManager(getActivityContext, 4)
        recyclerView.setHasFixedSize(true)

        getActivityContext!!.unlockMenu()

        installedAppsList = ArrayList()

        Handler(Looper.getMainLooper()).postDelayed({
            setAdapter(getInstalledApps())
        }, 500)

        return binding?.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.resetTitlebar()
    }

    fun setAdapter(dataInArrayList: ArrayList<AppModel>) {
        recyclerView.adapter = AppsAdapter(getActivityContext!!, dataInArrayList)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getInstalledApps(): ArrayList<AppModel> {
        val packs = getActivityContext?.packageManager?.getInstalledPackages(0)
        for (i in packs?.indices!!) {
            val p = packs[i]
            if (!isSystemPackage(p)) {
                val appName =
                    p.applicationInfo.loadLabel(getActivityContext?.packageManager!!).toString()
                val icon = p.applicationInfo.loadIcon(getActivityContext?.packageManager!!)
                val packages = p.applicationInfo.packageName
                Log.d("packages", "getInstalledApps: " + packages)
                installedAppsList.add(AppModel(appName, icon, packages))
            }
        }
        return installedAppsList
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    fun saveListInLocal(list: ArrayList<AppModel>) {
        val prefs = getActivityContext!!.getSharedPreferences("prefsName", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("apps", json)
        editor.apply()
        Log.d("apps_list", "saveListInLocal: " + prefs.getString("apps", null))
        //setAdapter(getDataInArrayList())
    }

    fun getDataInArrayList(): ArrayList<AppModel> {
        val prefs = getActivityContext!!.getSharedPreferences("prefsName", Activity.MODE_PRIVATE)
        val json = prefs.getString("apps", null)
        val type = object : TypeToken<ArrayList<AppModel>>() {}.type
        return Gson().fromJson(json, type)
    }
}