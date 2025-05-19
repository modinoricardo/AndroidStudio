package com.example.proyectofinalricardomitienda.state

import com.example.proyectofinalricardomitienda.service.ProductApiService
import com.example.proyectofinalricardomitienda.entities.ResponseProduct
import com.example.proyectofinalricardomitienda.service.ShoppingCartApiService
import com.example.proyectofinalricardomitienda.entities.ShoppingCartProduct
import com.example.proyectofinalricardomitienda.service.TokenApiService
import com.example.proyectofinalricardomitienda.entities.LoginRequest
import com.example.proyectofinalricardomitienda.exceptions.NegativeNumberException
import com.example.proyectofinalricardomitienda.exceptions.NoStockException
import com.example.proyectofinalricardomitienda.util.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainState {
    private var base = Util.URL + "api/app/v1/"
    private val token = "Bearer ${Util.accessToken}"

    private fun retrofit() = Retrofit.Builder()
        .baseUrl(base)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApiService::class.java)

    suspend fun returnAllProductsByNamePaged(search: String, page: Int): ResponseProduct =
        retrofit().getAllProductsByNamePaged(search, page, token).body() ?: ResponseProduct()

    suspend fun returnAllProductsByCategoryPaged(cat: Long, page: Int): ResponseProduct =
        retrofit().getAllProductsByCategoryPaged(cat, page, token).body() ?: ResponseProduct()

    suspend fun returnAllProductsByAllPaged(search: String, cat: Long, page: Int): ResponseProduct =
        retrofit().getAllProductsByAllPaged(search, cat, page, token).body() ?: ResponseProduct()

    suspend fun returnAllProductsPaged(page: Int): ResponseProduct =
        retrofit().getAllProductsPaged(token, page).body() ?: ResponseProduct()

    suspend fun returnAllProducts(): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)

        return service.getAllProducts(token).body() ?: ResponseProduct()
    }

    suspend fun returnAllProductsByName(search: String): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)

        return service.getAllProductsByName(search,token).body() ?: ResponseProduct()
    }

    suspend fun returnAllProductsByCategory(cat: Long): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)

        return service.getAllProductsByCategory(cat, token).body() ?: ResponseProduct()
    }

    suspend fun returnAllProductsByAll(search: String, cat: Long): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)

        return service.getAllProductsByAll(search, cat, token).body() ?: ResponseProduct()
    }

    suspend fun returnAllCart(): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.getShoppingCart(token).body()!!
    }

    suspend fun addProductToCart(productId: Long): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)
        val call= service.addProduct(productId, token)

        if (call.code()==200) {
            val body = call.body()
            return body!!
        }else if(call.code()==409){
            throw NoStockException("No hay suficiente stock")
        }else if(call.code()==400){
            throw NegativeNumberException("Pon un valor positivo")
        }else{
            throw Exception("Algo ha pasado")
        }
    }

    suspend fun addManyProductToCart(productId: Long, quantity: Int): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        val call= service.addManyProduct(productId, quantity, token);

        if (call.code()==200) {
            val body = call.body()
            return body!!
        }else if(call.code()==409){
            throw NoStockException("No hay suficiente stock")
        }else if(call.code()==400){
            throw NegativeNumberException("Pon un valor positivo")
        }else{
            throw Exception("Algo ha pasado")
        }
    }

    suspend fun deleteOneProductFromCart(shoppingCartProductId: Long): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.deleteOneProduct(shoppingCartProductId, token).body() !!
    }

    suspend fun deleteAllProductsFromCart(): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)
        return service.deleteAllProducts(token).body() !!
    }

    suspend fun login(user: String, password: String): Int {
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(TokenApiService::class.java)

        val loginRequest = LoginRequest(user, password)

        try {
            val call = service.login(loginRequest)
            val response = call.body()
            Util.accessToken = response!!.accessToken
            Util.refrehToken = response.refreshToken
            return 200
        } catch (e: Exception) {
            return 500
        }
    }

}