package com.example.proyectofinalricardomitienda.service

import com.example.proyectofinalricardomitienda.entities.ShoppingCartProduct
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingCartApiService {
    @GET("cart")
    suspend fun getShoppingCart(@Header("Authorization") auth:String): Response<ShoppingCartProduct>

    @POST("cart/{productId}")
    suspend fun addProduct(@Path("productId") productId:Long, @Header("Authorization") auth:String):Response<ShoppingCartProduct>

    @POST("cart/{productId}/{quantity}")
    suspend fun addManyProduct(@Path("productId") productId:Long, @Path("quantity") quantity: Int, @Header("Authorization") auth:String):Response<ShoppingCartProduct>

    @DELETE("cart/{shoppingCartProductId}")
    suspend fun deleteOneProduct(@Path("shoppingCartProductId") shoppingCartProductId:Long, @Header("Authorization") auth:String):Response<ShoppingCartProduct>

    @DELETE("cart")
    suspend fun deleteAllProducts(@Header("Authorization") auth:String):Response<ShoppingCartProduct>
}