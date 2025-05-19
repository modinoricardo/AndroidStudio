package com.example.proyectofinalricardomitienda.view

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalricardomitienda.R

class ProductView (itemView: View): RecyclerView.ViewHolder(itemView) {
    val productImage= itemView.findViewById<View>(R.id.productImage) as ImageView
    val txtPName= itemView.findViewById<View>(R.id.txtPName) as TextView
    val txtPDescription= itemView.findViewById<View>(R.id.txtPDescription) as TextView
    val txtPPrice= itemView.findViewById<View>(R.id.txtPPrice) as TextView
    val txtPCategory= itemView.findViewById<View>(R.id.txtPCategory) as TextView
    val btnAddToCart= itemView.findViewById<View>(R.id.btnAddToCart) as Button
}