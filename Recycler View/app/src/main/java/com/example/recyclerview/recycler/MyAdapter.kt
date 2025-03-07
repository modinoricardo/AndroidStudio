package com.example.recyclerview.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R

class MyAdapter(private val dataSet : List<String>) : RecyclerView.Adapter<MyView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fila, parent, false);

        return MyView(view);
    }

    override fun getItemCount(): Int {
        return dataSet.size;
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.tvAnimales.text = dataSet[position];
    }
}