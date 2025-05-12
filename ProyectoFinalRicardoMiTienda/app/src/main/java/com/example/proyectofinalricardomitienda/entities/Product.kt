package com.example.proyectofinalricardomitienda.entities

data class Product (
    val id: Long,
    val categoryName: String,
    val name: String,
    val description: String,
    val productDetail: String,
    val stock: Int,
    val price: Double,
    val imageUrl: String
)
