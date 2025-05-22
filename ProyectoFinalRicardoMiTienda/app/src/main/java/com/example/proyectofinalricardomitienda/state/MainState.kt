package com.example.proyectofinalricardomitienda.state

import com.example.proyectofinalricardomitienda.entities.Category
import com.example.proyectofinalricardomitienda.entities.CategoryResponse
import com.example.proyectofinalricardomitienda.service.ProductApiService
import com.example.proyectofinalricardomitienda.entities.ResponseProduct
import com.example.proyectofinalricardomitienda.service.ShoppingCartApiService
import com.example.proyectofinalricardomitienda.entities.ShoppingCartProduct
import com.example.proyectofinalricardomitienda.service.TokenApiService
import com.example.proyectofinalricardomitienda.entities.LoginRequest
import com.example.proyectofinalricardomitienda.exceptions.NegativeNumberException
import com.example.proyectofinalricardomitienda.exceptions.NoRecuperaProductsException
import com.example.proyectofinalricardomitienda.exceptions.NoStockException
import com.example.proyectofinalricardomitienda.service.CategoryApiService
import com.example.proyectofinalricardomitienda.util.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainState {
    private var baseUrl = Util.URL + "api/app/v1/"
    private val token = "Bearer ${Util.accessToken}"

    private val service: ProductApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }

    suspend fun recuperarProductos(
        search: String?    = null,
        categoriaId: Long? = null,
        pagina: Int        = 1,
        tamanio: Int       = 5
    ): ResponseProduct? {
        val resp = service.getProducts(
            search     = search,
            cat        = categoriaId,
            pageNumber = pagina,
            pageSize   = tamanio,
            auth       = token
        )
        if (!resp.isSuccessful) {
            throw NoRecuperaProductsException("Error HTTP ${resp.code()} al recuperar productos")
        }
        return resp.body()
    }

    suspend fun getAllCategories(): CategoryResponse {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CategoryApiService::class.java)
        val response = service.getCategories(token)
        return response.body()!!
    }

    suspend fun returnAllCart(): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.getShoppingCart(token).body()!!
    }

    suspend fun addProductToCart(productId: Long, quantity: Int = 1): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        val call = service.addManyProduct(productId, quantity, token);

        if (call.code() == 200) {
            val body = call.body()
            return body!!
        } else if (call.code() == 409) {
            throw NoStockException("No hay suficiente stock")
        } else if (call.code() == 400) {
            throw NegativeNumberException("Pon un valor positivo")
        } else {
            throw Exception("Algo ha pasado")
        }
    }

    suspend fun deleteOneProductFromCart(shoppingCartProductId: Long): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)

        return service.deleteOneProduct(shoppingCartProductId, token).body()!!
    }

    suspend fun deleteAllProductsFromCart(): ShoppingCartProduct {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingCartApiService::class.java)
        return service.deleteAllProducts(token).body()!!
    }

    suspend fun login(user: String, password: String): Int {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
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
