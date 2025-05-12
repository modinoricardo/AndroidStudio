package com.example.proyectofinalricardomitienda

import com.example.proyectofinalricardomitienda.entities.Pageable
import com.example.proyectofinalricardomitienda.entities.Product
import com.example.proyectofinalricardomitienda.entities.Sort

data class ResponseProduct(
    val content: List<Product> = emptyList(),
    val pageable: Pageable = Pageable(),
    val last: Boolean = false,
    val totalElements: Int = 0,
    val totalPages: Int = 0,
    val size: Int = 0,
    val number: Int = 0,
    val sort: Sort = Sort(),
    val first: Boolean = true,
    val numberOfElements: Int = 0,
    val empty: Boolean = true
)
