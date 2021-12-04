package com.example.nasa.ui.section

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.service.apod.ApodVO
import com.example.nasa.service.mars.PhotoMars

class RecyclerViewAdapter(val ctx: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolderMars>() {
    var items = ArrayList<PhotoMars>()


    class MyViewHolderMars(view: View, val ctx: Context) : RecyclerView.ViewHolder(view) {
        val imsSrc = view.findViewById<ImageView>(R.id.im)

        fun bind(data: PhotoMars) {
            val url = data.imgSource
            Glide
                .with(ctx)
                .load(url)
                .into(imsSrc)
        }
    }

    fun setUpdateData(items: ArrayList<PhotoMars>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderMars {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mars, parent, false)
        return MyViewHolderMars(view, ctx)
    }

    override fun onBindViewHolder(holder: MyViewHolderMars, position: Int) {
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int = items.size
}