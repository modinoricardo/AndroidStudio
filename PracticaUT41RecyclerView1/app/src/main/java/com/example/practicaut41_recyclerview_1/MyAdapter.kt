package com.example.practicaut41_recyclerview_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaut41_recyclerview_1.model.ColorR

class MyAdapter(private val dataSet: Array<ColorR>) : RecyclerView.Adapter<MyViewHolder>() {
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
    }
}