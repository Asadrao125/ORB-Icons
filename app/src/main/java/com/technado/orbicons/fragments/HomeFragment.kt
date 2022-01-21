package com.technado.orbicons.fragments

import android.annotation.SuppressLint
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
    lateinit var appList: ArrayList<AppModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        getActivityContext()?.unlockMenu()

        recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = GridLayoutManager(getActivityContext, 4)
        recyclerView.setHasFixedSize(true)

        appList = ArrayList()
        installedAppsList = ArrayList()

        Handler(Looper.getMainLooper()).postDelayed({
            appList.clear()
            appList = getInstalledApps()
            setAdapter(recyclerView, appList)
        }, 500)

        return binding?.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
       titlebar.resetTitlebar()
    }

    fun setAdapter(recyclerView: RecyclerView, appList: ArrayList<AppModel>) {
        recyclerView.adapter = AppsAdapter(getActivityContext!!, appList)
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
}