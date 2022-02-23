package com.fictivestudios.orb_icons.adapter

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.activities.MainActivity
import com.fictivestudios.orb_icons.helper.IconPacks
import com.fictivestudios.orb_icons.model.IconPacksModel

class DownloadIconPackAdapter(var context: MainActivity, var list: ArrayList<IconPacksModel>) :
    RecyclerView.Adapter<DownloadIconPackAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_icon_pack_downloads, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvPackName.text = list.get(position).packName
        holder.tvPackPrice.text = list.get(position).packPrice

        if (list.get(position).isPackPurchased == 1) {
            holder.itemView.isEnabled = true
            holder.itemView.alpha = .9f
        } else {
            holder.itemView.isEnabled = false
            holder.itemView.alpha = .3f
        }

        holder.image.setOnClickListener(View.OnClickListener {
            if (list.get(position).isPackPurchased == 1) {
                viewIconPack(position)
            } else {
                Toast.makeText(context, "Pack Not Purchased", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun viewIconPack(position: Int) {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_view_icon_pack)
        val imageRecyclerView = dialog.findViewById(R.id.imageRecyclerView) as RecyclerView
        val tvIconPackName = dialog.findViewById(R.id.tvIconPackName) as TextView
        val imgCross = dialog.findViewById(R.id.imgCross) as ImageView

        imageRecyclerView.layoutManager = GridLayoutManager(context, 4)
        imageRecyclerView.setHasFixedSize(true)
        var listDrawable: ArrayList<Drawable> = ArrayList()
        val nameList: Array<out String> = context.resources.getStringArray(R.array.iconPacks)
        tvIconPackName.text = nameList.get(position)

        if (position == 0) {
            listDrawable = IconPacks.getIconPack1(context)
        } else if (position == 1) {
            listDrawable = IconPacks.getIconPack2(context)
        } else if (position == 2) {
            listDrawable = IconPacks.getIconPack3(context)
        } else if (position == 3) {
            listDrawable = IconPacks.getIconPack4(context)
        } else if (position == 4) {
            listDrawable = IconPacks.getIconPack5(context)
        }
        imageRecyclerView.adapter = ImageAdapter(context, listDrawable)

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
        var tvPackName: TextView
        var tvPackPrice: TextView
        var image: ImageView

        init {
            tvPackName = itemView.findViewById(R.id.tvPackName)
            tvPackPrice = itemView.findViewById(R.id.tvPackPrice)
            image = itemView.findViewById(R.id.image)
        }
    }
}