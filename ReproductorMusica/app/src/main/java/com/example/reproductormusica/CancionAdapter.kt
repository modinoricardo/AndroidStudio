package com.example.reproductormusica

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CancionAdapter(
    private val canciones: List<Pair<String, Uri>>,
    private val onItemClick: (Uri, String) -> Unit
) : RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {

    class CancionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.textTituloCancion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cancion, parent, false)
        return CancionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val (titulo, uri) = canciones[position]
        holder.titulo.text = titulo
        holder.itemView.setOnClickListener {
            onItemClick(uri, titulo)
        }
    }

    override fun getItemCount() = canciones.size
}
