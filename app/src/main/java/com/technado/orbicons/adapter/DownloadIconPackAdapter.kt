package com.technado.orbicons.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.technado.orbicons.R

class DownloadIconPackAdapter(var context: Context, var list: ArrayList<String>) :
    RecyclerView.Adapter<DownloadIconPackAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_icon_pack, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var imageView: ImageView

        init {
            //imageView = itemView.findViewById(R.id.imageView)
        }
    }
}