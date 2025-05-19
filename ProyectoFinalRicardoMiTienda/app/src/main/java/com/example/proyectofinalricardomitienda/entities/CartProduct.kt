package com.example.proyectofinalricardomitienda.entities

data class CartProduct (
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double,
    val imageUrl:String
)