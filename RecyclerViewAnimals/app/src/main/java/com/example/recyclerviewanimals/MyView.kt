package com.example.recyclerviewanimals

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyView(itemView:View):RecyclerView.ViewHolder(itemView) {
    val tvAnimales: TextView= itemView.findViewById(R.id.tvAnimal)
}