package com.example.nasa.ui.section.apod

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nasa.R
import com.example.nasa.service.apod.ApodVO
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_start.view.*
import kotlinx.android.synthetic.main.item_apod.view.*

class RecyclerViewApodAdapter(var ctx: Context) : RecyclerView.Adapter<RecyclerViewApodAdapter.MyViewHolderApod>() {

    private var myListApod = emptyList<ApodVO>()

    inner class MyViewHolderApod(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewApodAdapter.MyViewHolderApod {
        return MyViewHolderApod(LayoutInflater.from(parent.context).inflate(R.layout.item_apod,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolderApod, position: Int) {
        holder.itemView.title_apod.text = myListApod[position].title.toString()
        holder.itemView.explanation.text = ""

        Glide.with(ctx)
            .load(myListApod[position].hdurl)
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .into(holder.itemView.image_back_apod)

        holder.itemView.setOnClickListener {
            if(!myListApod[position].isClick) {
                holder.itemView.explanation.text = myListApod[position].explanation.toString()
                myListApod[position].isClick = true
                holder.itemView.image_back_apod.setColorFilter(android.graphics.Color.parseColor("#403B36"),PorterDuff.Mode.DARKEN)

            }
            else if(myListApod[position].isClick){
                holder.itemView.explanation.text = ""
                holder.itemView.image_back_apod.clearColorFilter()
                myListApod[position].isClick = false
            }
        }

    }

    override fun getItemCount(): Int {
        return myListApod.size
    }

    fun setData(newList: List<ApodVO>){
        myListApod = newList
        notifyDataSetChanged()
    }

}