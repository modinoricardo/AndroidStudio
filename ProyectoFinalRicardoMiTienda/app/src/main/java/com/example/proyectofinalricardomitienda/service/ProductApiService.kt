package com.example.proyectofinalricardomitienda.service

import com.example.proyectofinalricardomitienda.entities.ResponseProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductApiService {

    @GET("products/find")
    suspend fun getAllProductsPaged(
        @Header("Authorization") auth: String,
        @Query("pageNumber") pageNumber: Int = 1
    ): Response<ResponseProduct>

    @GET("products/find")
    suspend fun getAllProductsByNamePaged(
        @Query("search") search: String,
        @Query("pageNumber") pageNumber: Int = 1,
        @Header("Authorization") auth: String
    ): Response<ResponseProduct>

    @GET("products/find")
    suspend fun getAllProductsByCategoryPaged(
        @Query("cat") cat: Long,
        @Query("pageNumber") pageNumber: Int = 1,
        @Header("Authorization") auth: String
    ): Response<ResponseProduct>

    @GET("products/find")
    suspend fun getAllProductsByAllPaged(
        @Query("search") search: String,
        @Query("cat") cat: Long,
        @Query("pageNumber") pageNumber: Int = 1,
        @Header("Authorization") auth: String
    ): Response<ResponseProduct>

    @GET("products/find")
    suspend fun getAllProducts(
        @Header("Authorization") auth:String
    ): Response<ResponseProduct>

    @GET("products/find")
    suspend fun getAllProductsByName(
        @Query("search") search:String,
        @Header("Authorization") auth:String
    ):Response<ResponseProduct>

    @GET("products/find")
    suspend fun getAllProductsByCategory(
        @Query("cat") cat: Long,
        @Header("Authorization") auth:String
    ):Response<ResponseProduct>

    @GET("products/find")
    suspend fun getAllProductsByAll(
        @Query("search") search: String,
        @Query("cat") cat: Long,
        @Header("Authorization") auth:String
    ):Response<ResponseProduct>
}