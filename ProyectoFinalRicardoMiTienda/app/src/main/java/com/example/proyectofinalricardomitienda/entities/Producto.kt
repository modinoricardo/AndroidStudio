package com.example.proyectofinalricardomitienda.entities

data class Producto(
    val id: Long,
    val category_id: Long,
    val name: String,
    val description: String,
    val productDetail: String,
    val stock: Int,
    val price: Double,
    val imageUrl: String?
)

