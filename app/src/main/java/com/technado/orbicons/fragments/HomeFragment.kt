package com.technado.orbicons.fragments

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Looper.getMainLooper
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
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
import com.technado.orbicons.helper.SharedPref
import com.technado.orbicons.helper.Titlebar
import com.technado.orbicons.model.AppModel
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {
    var binding: HomeFragmentBinding? = null
    lateinit var recyclerView: RecyclerView
    private lateinit var installedAppsList: ArrayList<AppModel>
    lateinit var sharedPref: SharedPref
    lateinit var adapter: AppsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        getActivityContext()?.unlockMenu()
        sharedPref = SharedPref(getActivityContext!!)

        recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = GridLayoutManager(getActivityContext, 4)
        recyclerView.setHasFixedSize(true)

        getActivityContext!!.unlockMenu()
        installedAppsList = ArrayList()

        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPref.read("apps", "").equals("")) {
                val list: ArrayList<AppModel> = getInstalledApps()

                if (!sharedPref.read("size", "").equals("" + list.size)) {
                    setAdapter(sharedPref.setAllAppsLocal(list))
                    sharedPref.write("size", "" + list.size)
                }
            } else {
                setAdapter(sharedPref.getAllAppsLocal())
            }
        }, 500)

        val someHandler = Handler(getMainLooper())
        someHandler.postDelayed(object : Runnable {
            override fun run() {
                binding?.tvTime?.setText(SimpleDateFormat("hh : mm a", Locale.US).format(Date()))
                someHandler.postDelayed(this, 1000)
            }
        }, 10)

        val sdf = SimpleDateFormat("EEEE, dd MMM")
        binding?.tvDate?.text = sdf.format(Date())

        binding?.edtSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val text = s.toString()
                if (text.length >= 0) {
                    val newlist: ArrayList<AppModel> = ArrayList()
                    val oldList: ArrayList<AppModel> = sharedPref.getAllAppsLocal()
                    for (l in 0 until oldList.size) {
                        val serviceName: String = oldList.get(l).name.toLowerCase()
                        if (serviceName.contains(s.toString().toLowerCase())) {
                            newlist.add(
                                AppModel(
                                    oldList.get(l).name,
                                    oldList.get(l).icon,
                                    oldList.get(l).packages
                                )
                            )
                        }
                    }
                    adapter = AppsAdapter(getActivityContext!!, newlist)
                    recyclerView.adapter = adapter
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        return binding?.root
    }

    fun convertBitmapToString(bitmap: Bitmap): String? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            )
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.resetTitlebar()
    }

    fun setAdapter(list: ArrayList<AppModel>) {
        adapter = AppsAdapter(getActivityContext!!, list)
        recyclerView.adapter = adapter
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getInstalledApps(): ArrayList<AppModel> {
        val packs = getActivityContext?.packageManager?.getInstalledPackages(0)
        for (i in packs?.indices!!) {
            val p = packs[i]
            if (!isSystemPackage(p)) {
                val appName =
                    p.applicationInfo.loadLabel(getActivityContext?.packageManager!!).toString()
                val icon = convertBitmapToString(
                    drawableToBitmap(
                        p.applicationInfo.loadIcon(getActivityContext?.packageManager!!)
                    )!!
                )
                val packages = p.applicationInfo.packageName
                Log.d("packages", "getInstalledApps: " + packages)
                installedAppsList.add(AppModel(appName, icon!!, packages))
            }
        }
        return installedAppsList
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}