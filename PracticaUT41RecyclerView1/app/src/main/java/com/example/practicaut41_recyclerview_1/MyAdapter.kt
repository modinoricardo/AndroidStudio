package com.example.practicaut41_recyclerview_1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaut41_recyclerview_1.model.ColorR

class MyAdapter(private val dataSet: List<ColorR>) : RecyclerView.Adapter<MyViewHolder>() {

    var clickPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val color = dataSet[position]
        holder.tvCodigoColor.text = color.codigo
        holder.tvNombreColor.text = color.nombre
        val fondo = Color.parseColor(color.codigo)
        holder.fondo.setBackgroundColor(fondo)

        if(position == clickPosition){
            holder.fondo.setBackgroundColor(Color.WHITE)

            val colorString = dataSet[position].codigo
            holder.tvNombreColor.setTextColor(Color.parseColor(colorString))
            holder.tvCodigoColor.setTextColor(Color.parseColor(colorString))
        }else {
            holder.fondo.setBackgroundColor(dataSet[position].codigo.toColorInt())
        }

        holder.fondo.setOnClickListener{
            notifyItemChanged(clickPosition)
            clickPosition = position
            notifyItemChanged(clickPosition)
        }

        holder.fondo.setOnLongClickListener{
            val colorString = dataSet[position].codigo
            holder.fondo.setBackgroundColor(Color.parseColor(colorString))
            holder.tvNombreColor.setTextColor(Color.WHITE)
            holder.tvCodigoColor.setTextColor(Color.WHITE)
            clickPosition=-1
            true
        }

    }
}