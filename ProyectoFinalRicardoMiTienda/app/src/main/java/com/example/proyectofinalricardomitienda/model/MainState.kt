package com.example.proyectofinalricardomitienda.model

import android.text.Editable
import com.example.proyectofinalricardomitienda.ProductApiService
import com.example.proyectofinalricardomitienda.ResponseProduct
import com.example.proyectofinalricardomitienda.ShoppingCartApiService
import com.example.proyectofinalricardomitienda.ShoppingCartProduct
import com.example.proyectofinalricardomitienda.TokenApiService
import com.example.proyectofinalricardomitienda.entities.LoginRequest
import com.example.proyectofinalricardomitienda.entities.TokenResponse
import com.example.proyectofinalricardomitienda.util.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainState {
    private var cadena = Util.URL + "api/app/v1/"

    suspend fun returnAllProducts(): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)
        val auth = "Bearer ${Util.accessToken}"
        return service.getAllProducts(auth).body() ?: ResponseProduct()
    }

    suspend fun returnAllProductsByName(search: String): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)

        return service.getAllProductsByName(search).body() ?: ResponseProduct()
    }

    suspend fun returnAllProductsByCategory(cat: Long): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)

        return service.getAllProductsByCategory(cat).body() ?: ResponseProduct()
    }

    suspend fun returnAllProductsByAll(search: String, cat: Long): ResponseProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductApiService::class.java)

        return service.getAllProductsByAll(search, cat).body() ?: ResponseProduct()
    }

    //Carrito
    suspend fun returnAllCart(): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.getShoppingCart().body() ?: ShoppingCartProduct()
    }

    suspend fun addProductToCart(productId: Long): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.addProduct(productId).body() ?: ShoppingCartProduct()
    }

    suspend fun addManyProductToCart(productId: Long, quantity: Int): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.addManyProduct(productId, quantity).body() ?: ShoppingCartProduct()
    }

    suspend fun deleteOneProductFromCart(shoppingCartProductId: Long): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.deleteOneProduct(shoppingCartProductId).body() ?: ShoppingCartProduct()
    }

    suspend fun deleteAllProductsFromCart(): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.deleteAllProducts().body() ?: ShoppingCartProduct()
    }

    suspend fun login(user: String, password: String): Int {
        val retrofit = Retrofit.Builder()
            .baseUrl(cadena)
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