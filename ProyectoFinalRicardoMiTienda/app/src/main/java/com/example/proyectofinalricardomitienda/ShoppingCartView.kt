package com.example.proyectofinalricardomitienda

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ShoppingCartView (itemView: View): RecyclerView.ViewHolder(itemView) {
    val labelSCPrice= itemView.findViewById<View>(R.id.labelSCPrice) as TextView
    val labelSCQuantity= itemView.findViewById<View>(R.id.labelSCQuantity) as TextView
    val labelSCTotal= itemView.findViewById<View>(R.id.labelSCTotal) as TextView
    val txtSCName= itemView.findViewById<View>(R.id.txtSCName) as TextView
    val txtSCQuantity= itemView.findViewById<View>(R.id.txtSCQuantity) as TextView
    val txtSCPrice= itemView.findViewById<View>(R.id.txtSCPrice) as TextView
    val txtSCTotalPrice= itemView.findViewById<View>(R.id.txtSCTotalPrice) as TextView
    val fondo= itemView.findViewById<View>(R.id.fondo) as CardView
}