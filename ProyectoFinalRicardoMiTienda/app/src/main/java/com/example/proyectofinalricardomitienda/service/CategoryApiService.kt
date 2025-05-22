package com.example.proyectofinalricardomitienda.service

import com.example.proyectofinalricardomitienda.entities.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CategoryApiService {
    @GET("categories/find")
    suspend fun getCategories(
        @Header("Authorization") auth:String
    ): Response<CategoryResponse>
}