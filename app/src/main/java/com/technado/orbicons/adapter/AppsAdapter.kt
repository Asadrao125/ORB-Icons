package com.technado.orbicons.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technado.orbicons.R
import com.technado.orbicons.helper.RecyclerItemClickListener
import com.technado.orbicons.model.AppModel
import java.lang.Exception

class AppsAdapter(var context: Context, var list: List<AppModel>) :
    RecyclerView.Adapter<AppsAdapter.MyViewHolder>() {
    lateinit var iconNew: Drawable
    lateinit var adapter: ImageAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_apps, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.appName.text = list.get(position).name
        holder.image.setImageDrawable(list.get(position).icon)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent =
                context.packageManager.getLaunchIntentForPackage(list.get(position).packages)
            context.startActivity(intent)
        })

        holder.itemView.setOnLongClickListener {
            optionsDialog(list.get(position).name, position)
            return@setOnLongClickListener true
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

    @SuppressLint("NewApi")
    fun createWebActivityShortcut(position: Int) {
        val shortcutManager = ContextCompat.getSystemService<ShortcutManager>(
            context,
            ShortcutManager::class.java
        )
        if (shortcutManager!!.isRequestPinShortcutSupported) {
            val pinShortcutInfoBuilder = ShortcutInfo.Builder(context, list.get(position).name)
            pinShortcutInfoBuilder.setShortLabel(list.get(position).name)
            val intent =
                context.packageManager.getLaunchIntentForPackage(list.get(position).packages)
            pinShortcutInfoBuilder.setIntent(intent!!)
            pinShortcutInfoBuilder.setIcon(
                Icon.createWithResource(
                    context,
                    R.drawable.ic_launcher_background
                )
            )
            val pinShortcutInfo = pinShortcutInfoBuilder.build()

            val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(
                pinShortcutInfo
            )
            val successCallback = PendingIntent.getBroadcast(
                context, 0,
                pinnedShortcutCallbackIntent, 0
            )
            shortcutManager.requestPinShortcut(
                pinShortcutInfo,
                successCallback.intentSender
            )
        }
    }

    fun unInstallApp(pkgName: String) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse(pkgName)
        context.startActivity(intent)
    }

    private fun optionsDialog(title: String, position: Int) {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_options)
        val tvTitle = dialog.findViewById(R.id.tvTitle) as TextView
        val btnClose = dialog.findViewById(R.id.btnClose) as Button
        val unInstall = dialog.findViewById(R.id.unInstall) as LinearLayout
        val share = dialog.findViewById(R.id.share) as LinearLayout
        val edit = dialog.findViewById(R.id.edit) as LinearLayout
        tvTitle.text = title

        btnClose.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        unInstall.setOnClickListener(View.OnClickListener {

        })

        share.setOnClickListener(View.OnClickListener {

        })

        edit.setOnClickListener(View.OnClickListener {
            editDialog(title, position)
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
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_edit)
        val tvTitle = dialog.findViewById(R.id.tvTitle) as TextView
        val imgApp = dialog.findViewById(R.id.imgApp) as ImageView
        val edtTitle = dialog.findViewById(R.id.edtTitle) as EditText
        val btnUpdate = dialog.findViewById(R.id.btnUpdate) as Button
        val btnClose = dialog.findViewById(R.id.btnClose) as Button
        val imageRecyclerView = dialog.findViewById(R.id.imageRecyclerView) as RecyclerView

        btnClose.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        val imageList: ArrayList<Drawable> = ArrayList()
        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_share)!!)
        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_uninstall)!!)
        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_edit)!!)

        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_share)!!)
        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_uninstall)!!)
        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_edit)!!)

        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_share)!!)
        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_uninstall)!!)
        imageList.add(ContextCompat.getDrawable(context, R.drawable.ic_edit)!!)

        imageRecyclerView.layoutManager = GridLayoutManager(context, 4)
        imageRecyclerView.setHasFixedSize(true)

        adapter = ImageAdapter(context, imageList)
        imageRecyclerView.adapter = adapter

        tvTitle.text = "Edit - " + title
        imgApp.setImageDrawable(list.get(position).icon)
        edtTitle.setText(title)

        imageRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context, imageRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {

                        imgApp.setImageDrawable(imageList.get(position))

                        iconNew = imageList.get(position)
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
            } else {
                list.get(position).name = edtTitle.text.toString().trim()
                list.get(position).icon = iconNew
                notifyItemChanged(position)
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
}