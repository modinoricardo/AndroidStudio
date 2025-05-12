package com.example.proyectofinalricardomitienda

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingCartApiService {
    @GET("cart")
    suspend fun getShoppingCart(): Response<ShoppingCartProduct>

    @POST("cart/{productId}")
    suspend fun addProduct(@Path("productId") productId:Long):Response<ShoppingCartProduct>

    @POST("cart/{prodcutId}/{quantity}")
    suspend fun addManyProduct(@Path("productId") productId:Long, @Path("quantity") quantity: Int):Response<ShoppingCartProduct>

    @DELETE("cart/{shoppingCartProductId}")
    suspend fun deleteOneProduct(@Path("shoppingCartProductId") shoppingCartProductId:Long):Response<ShoppingCartProduct>

    @DELETE("cart")
    suspend fun deleteAllProducts():Response<ShoppingCartProduct>
}