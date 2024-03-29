package com.fictivestudios.orb_icons.fragments

import android.annotation.SuppressLint
import android.app.Activity.*
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper.getMainLooper
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fictivestudios.demoapp.base.BaseFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.adapter.AppsAdapter
import com.fictivestudios.orb_icons.helper.SharedPref
import com.fictivestudios.orb_icons.helper.Titlebar
import com.fictivestudios.orb_icons.model.AppModel
import com.fictivestudios.orb_icons.databinding.HomeFragmentBinding
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

        adapter = AppsAdapter(getActivityContext!!)
        recyclerView.adapter = adapter

        getActivityContext!!.unlockMenu()
        installedAppsList = ArrayList()

        adapterSet()

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
                val text = s.toString().trim()
                if (text.length > 0) {
                    adapter.filter(text)
                    binding?.imgClear?.visibility = View.VISIBLE
                } else {
                    adapter.filter(text)
                    binding?.imgClear?.visibility = View.GONE
                    binding?.edtSearch?.clearFocus()
                    val imm: InputMethodManager =
                        (getActivityContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)!!
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding?.imgClear?.setOnClickListener(View.OnClickListener {
            binding?.edtSearch?.setText("")
            binding?.edtSearch?.clearFocus()
            val imm: InputMethodManager =
                (getActivityContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)!!
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        })

        return binding?.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                Log.d("req_code", "onActivityResult: user accepted the (un)install")
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("req_code", "onActivityResult: user canceled the (un)install")
            } else if (resultCode == RESULT_FIRST_USER) {
                Log.d("req_code", "onActivityResult: failed to (un)install")
            }
        }
    }

    fun adapterSet() {
        val list: ArrayList<AppModel> = getInstalledApps()
        Handler(getMainLooper()).post({
            if (sharedPref.read("apps", "").equals("")) {
                setAdapter(sharedPref.setAllAppsLocal(list))
                sharedPref.write("size", +list.size)
            } else if (sharedPref.read("size", 0) != list.size) {

                val localList = sharedPref.getAllAppsLocal()
                val listDiff: ArrayList<AppModel> =
                    list.filter { it.packages !in localList.map { item -> item.packages } } as ArrayList<AppModel>
                listDiff.addAll(sharedPref.getAllAppsLocal())
                adapter.addAll(sharedPref.setAllAppsLocal(listDiff))
                adapter.notifyDataSetChanged()

            } else {
                setAdapter(sharedPref.getAllAppsLocal())
            }
        })
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
        adapter.addAll(list)
        adapter.notifyDataSetChanged()
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
                installedAppsList.add(AppModel(appName, icon!!, packages, icon, 0))
            }
        }
        return installedAppsList
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}