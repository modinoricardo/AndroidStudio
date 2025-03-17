package com.example.recyclerviewanimals

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val dataSet: List<String>) : RecyclerView.Adapter<MyView>() {

    var clickPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyView(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.tvAnimales.text = dataSet[position]

        if(position == clickPosition){
            holder.tvAnimales.setTextColor(Color.CYAN)
            holder.tvAnimales.setBackgroundColor(Color.MAGENTA)
        }else {
            holder.tvAnimales.setTextColor(Color.BLACK)
            holder.tvAnimales.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.tvAnimales.setOnClickListener{
            notifyItemChanged(clickPosition)
            clickPosition = position
            notifyItemChanged(clickPosition)
        }

//        holder.tvAnimales.setOnClickListener{
//            holder.tvAnimales.setTextColor(Color.BLACK)
//            holder.tvAnimales.setBackgroundColor(Color.RED)
//        }

//        holder.tvAnimales.setOnLongClickListener(){
//            holder.tvAnimales.setTextColor(Color.BLACK)
////            holder.tvAnimales.setBackgroundColor(Color.BLACK)
//            holder.tvAnimales.setBackgroundColor(Color.TRANSPARENT)
//            true
//        }

    }

}