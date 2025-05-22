package com.example.proyectofinalricardomitienda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.entities.CartProduct
import com.example.proyectofinalricardomitienda.entities.ShoppingCartProduct
import com.example.proyectofinalricardomitienda.view.ShoppingCartView

class ShoppingCartAdapter(private val dataSet: ShoppingCartProduct) : RecyclerView.Adapter<ShoppingCartView>() {
    private lateinit var myContexto: Context
    var positionClicked = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_shopping_cart, parent, false)
        return ShoppingCartView(view)
    }

    override fun getItemCount() = dataSet.appCartItems.size

    fun getItem(position: Int): CartProduct? =
         dataSet.appCartItems.getOrNull(position)


    override fun onBindViewHolder(holder: ShoppingCartView, position: Int) {
        val product = dataSet.appCartItems[position]

        holder.txtSCName.text = product.productName
        holder.txtSCQuantity.text = product.quantity.toString()
        holder.txtSCPrice.text = product.unitPrice.toString()
        holder.txtSCTotalPrice.text = product.totalPrice.toString()

        // Pinta el fondo solo si está seleccionado
        if (position == positionClicked) {
            holder.fondo.setBackgroundColor(android.graphics.Color.GRAY)
        } else {
            holder.fondo.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        }

        holder.fondo.setOnClickListener {
            val previous = positionClicked
            if (previous == position) {
                // Si vuelves a pulsar el mismo, deselecciona
                positionClicked = RecyclerView.NO_POSITION
                notifyItemChanged(position)
            } else {
                // Selecciona este y deselecciona el anterior
                positionClicked = position
                notifyItemChanged(previous)
                notifyItemChanged(position)
            }
        }
    }
}