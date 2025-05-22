package com.example.apidog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter (private val dataSet: DogRespuesta) : RecyclerView.Adapter<MyView>(){

    lateinit var myContexto : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        myContexto = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)
        return MyView(view)
    }

    override fun getItemCount() = dataSet.message!!.size

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val url : String = dataSet.message!![position]
        Glide.with(myContexto)
//            .load(url)
//            .load("http://10.0.2.2:8000/test/app/img/categories/coffee.png")
            .load("https://www.lavanguardia.com/peliculas-series/images/profile/1956/1/w300/mO4yaPr5HwMVaYY2liJyIZfsg9d.jpg")
            .into(holder.imV1)
    }

}