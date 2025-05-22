package com.example.proyectofinalricardomitienda.service

import com.example.proyectofinalricardomitienda.entities.ResponseProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductApiService {

    @GET("products/find")
    suspend fun getProducts(
        @Query("search") search: String? = null,
        @Query("cat") cat: Long? = null,
        @Query("pageNumber") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 5,
        @Header("Authorization") auth: String
    ): Response<ResponseProduct>

}