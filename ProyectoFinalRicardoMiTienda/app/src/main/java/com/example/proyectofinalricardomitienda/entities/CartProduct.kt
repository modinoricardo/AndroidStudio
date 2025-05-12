package com.example.proyectofinalricardomitienda.entities

data class CartProduct (
    val id: Long,
    val name: String,
    val quantity: Int,
    val price: Double,
    val totalPrice: Double
)