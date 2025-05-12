package com.example.proyectofinalricardomitienda

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShoppingCartAdapter(private val dataSet: ShoppingCartProduct) : RecyclerView.Adapter<ShoppingCartView>() {
    private lateinit var myContexto: Context
    var positionClicked: Int = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartView {
        myContexto = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_shopping_cart, parent, false)
        return ShoppingCartView(view)
    }

    override fun getItemCount() = dataSet.shoppingCartProducts!!.size


    override fun onBindViewHolder(holder: ShoppingCartView, position: Int) {
        val product = dataSet.shoppingCartProducts[position]
        if (product != null) {
            holder.txtSCName.text = product.name
            holder.txtSCQuantity.text = product.quantity.toString()
            holder.txtSCPrice.text = product.price.toString()
            holder.txtSCTotalPrice.text = product.totalPrice.toString()
            if(position==positionClicked){
                holder.fondo.setBackgroundColor(android.graphics.Color.CYAN)
            }
            holder.fondo.setOnClickListener{
                notifyItemChanged(positionClicked)
                positionClicked=position
                notifyItemChanged(positionClicked)
            }
            holder.fondo.setOnLongClickListener {
                positionClicked=RecyclerView.NO_POSITION
                holder.fondo.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                true
            }
        }
    }
}