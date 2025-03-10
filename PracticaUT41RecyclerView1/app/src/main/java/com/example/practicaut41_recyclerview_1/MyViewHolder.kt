package com.example.practicaut41_recyclerview_1

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCodigoColor:TextView = itemView.findViewById(R.id.textView5)
    val tvNombreColor:TextView = itemView.findViewById(R.id.textView6)
    val fondo : LinearLayout = itemView.findViewById(R.id.fondo)
}