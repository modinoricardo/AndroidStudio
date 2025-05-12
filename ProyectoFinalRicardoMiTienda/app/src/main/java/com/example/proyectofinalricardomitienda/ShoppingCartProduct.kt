package com.example.proyectofinalricardomitienda

import com.example.proyectofinalricardomitienda.entities.CartProduct

data class ShoppingCartProduct(
    val shoppingCartProducts: List<CartProduct> = emptyList(),
    val totalCartQuantity: Int = 0,
    val totalCartPrice: Double = 0.0
)
