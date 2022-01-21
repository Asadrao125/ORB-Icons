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

class ImageAdapter(var context: Context, var list: ArrayList<Drawable>) :
    RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    var selectedPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imageView.setImageDrawable(list.get(position))

        holder.imageView.setBackgroundColor(
            if (selectedPos == position) Color.parseColor("#E0E0E0") else Color.parseColor(
                "#FFFFFF"
            )
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.imageView)
        }
    }
}