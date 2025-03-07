package com.example.recyclerview.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R

class MyView(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val tvAnimales = itemView.findViewById<TextView>(R.id.tv1)

}