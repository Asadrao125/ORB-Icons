package com.fictivestudios.orb_icons.adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.activities.MainActivity
import com.fictivestudios.orb_icons.helper.IconPacks
import com.fictivestudios.orb_icons.helper.RecyclerItemClickListener
import com.fictivestudios.orb_icons.helper.SharedPref
import com.fictivestudios.orb_icons.model.AppModel
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class AppsAdapter(var context: MainActivity) :
    RecyclerView.Adapter<AppsAdapter.MyViewHolder>() {
    var iconNew: String = ""
    lateinit var adapter: ImageAdapter
    lateinit var sharedPref: SharedPref
    var copyList: ArrayList<AppModel> = ArrayList()
    var list: ArrayList<AppModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_apps, parent, false)
        sharedPref = SharedPref(context)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.appName.text = list.get(position).name
        Glide.with(context).load(StringToBitMap(list.get(position).icon))
            .placeholder(R.drawable.change_password).into(holder.image)

        holder.itemView.setOnClickListener(View.OnClickListener {
            //com.technado.orbicons
            if (!list.get(position).packages.equals("com.fictivestudios.orb_icons")) {
                val intent =
                    context.packageManager.getLaunchIntentForPackage(list.get(position).packages)
                context.startActivity(intent)
            }
        })

        holder.itemView.setOnLongClickListener {
            optionsDialog(list.get(position).name, position)
            return@setOnLongClickListener true
        }
    }

    fun filter(sequence: CharSequence) {
        list.clear()
        if (!TextUtils.isEmpty(sequence.toString())) {
            for (a in copyList.size - 1 downTo 0) {
                if (copyList.get(a).name.contains(sequence, true)) {
                    list.add(copyList.get(a))
                }
            }
        } else {
            list.addAll(copyList)
        }
        notifyDataSetChanged()
    }

    fun addAll(productArrayList: ArrayList<AppModel>) {
        list.clear()
        productArrayList.let { list.addAll(it) }

        this.copyList.clear()
        productArrayList.let { this.copyList.addAll(it) }
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appName: TextView
        var image: ImageView

        init {
            appName = itemView.findViewById(R.id.appName)
            image = itemView.findViewById(R.id.image)
        }
    }

    fun unInstallApp(pkgName: String, position: Int) {
        val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.fromParts("package", pkgName, null)
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent)
        }
        list.removeAt(position)
        notifyItemChanged(position)
        addAll(sharedPref.setAllAppsLocal(list))
        notifyDataSetChanged()
    }

    private fun optionsDialog(title: String, position: Int) {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_options)
        val tvTitle = dialog.findViewById(R.id.tvTitle) as TextView
        val unInstall = dialog.findViewById(R.id.unInstall) as LinearLayout
        val edit = dialog.findViewById(R.id.edit) as LinearLayout
        val appInfo = dialog.findViewById(R.id.appInfo) as LinearLayout
        val revertIcon = dialog.findViewById(R.id.revertIcon) as LinearLayout
        val imgCross = dialog.findViewById(R.id.imgCross) as ImageView
        tvTitle.text = title

        unInstall.alpha = 0.9f
        appInfo.alpha = 0.9f
        revertIcon.alpha = 0.9f
        edit.alpha = 0.9f

        if (list.get(position).packages.equals("com.fictivestudios.orb_icons")) {
            unInstall.isEnabled = false
            appInfo.isEnabled = false
            unInstall.alpha = 0.6f
            appInfo.alpha = 0.6f
        } else {
            unInstall.isEnabled = true
            appInfo.isEnabled = true
            unInstall.alpha = 0.9f
            appInfo.alpha = 0.9f
        }

        unInstall.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            unInstallApp(list.get(position).packages, position)
        })

        edit.setOnClickListener(View.OnClickListener {
            editDialog(title, position)
            dialog.dismiss()
        })

        revertIcon.setOnClickListener(View.OnClickListener {
            list.get(position).icon = list.get(position).defaultIcon
            notifyDataSetChanged()
            sharedPref.setAllAppsLocal(copyList)
            dialog.dismiss()
        })

        appInfo.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromParts("package", list.get(position).packages, null)
            intent.data = uri
            context.startActivity(intent)
        })

        imgCross.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun editDialog(title: String, position: Int) {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_edit)
        val tvTitle = dialog.findViewById(R.id.tvTitle) as TextView
        val imgApp = dialog.findViewById(R.id.imgApp) as ImageView
        val edtTitle = dialog.findViewById(R.id.edtTitle) as EditText
        val btnUpdate = dialog.findViewById(R.id.btnUpdate) as Button
        val btnClose = dialog.findViewById(R.id.btnClose) as Button
        val imageRecyclerView = dialog.findViewById(R.id.imageRecyclerView) as RecyclerView
        val spIconPacks = dialog.findViewById(R.id.spIconPacks) as Spinner
        val imgCross = dialog.findViewById(R.id.imgCross) as ImageView

        btnClose.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        imageRecyclerView.layoutManager = GridLayoutManager(context, 4)
        imageRecyclerView.setHasFixedSize(true)

        tvTitle.text = "Edit - " + title
        imgApp.setImageBitmap(StringToBitMap(list.get(position).icon))
        edtTitle.setText(title)

        imgCross.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        var listDrawable: ArrayList<Drawable> = ArrayList()

        spIconPacks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    listDrawable = IconPacks.getIconPack1(context)
                } else if (p2 == 1) {
                    listDrawable = IconPacks.getIconPack2(context)
                } else if (p2 == 2) {
                    listDrawable = IconPacks.getIconPack3(context)
                } else if (p2 == 3) {
                    listDrawable = IconPacks.getIconPack4(context)
                } else if (p2 == 4) {
                    listDrawable = IconPacks.getIconPack5(context)
                }
                adapter = ImageAdapter(context, listDrawable)
                imageRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        imageRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context, imageRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        imgApp.setImageDrawable(listDrawable.get(position))
                        iconNew =
                            convertBitmapToString(
                                drawableToBitmap(
                                    listDrawable.get(position)
                                )!!
                            )!!
                        adapter.selectedPos = position
                        adapter.notifyDataSetChanged()
                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }
                })
        )

        btnUpdate.setOnClickListener(View.OnClickListener {
            if (edtTitle.text.toString().trim().isEmpty()) {
                Toast.makeText(context, "Title Required", Toast.LENGTH_SHORT).show()
            } else if (iconNew.isEmpty()) {
                Toast.makeText(context, "Please Select Icon", Toast.LENGTH_SHORT).show()
            } else {
                list.get(position).name = edtTitle.text.toString().trim()
                list.get(position).icon = iconNew

                notifyDataSetChanged()
                Log.d("list_size", "editDialog: " + sharedPref.setAllAppsLocal(copyList).size)
                dialog.dismiss()
            }
        })

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
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
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
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
}