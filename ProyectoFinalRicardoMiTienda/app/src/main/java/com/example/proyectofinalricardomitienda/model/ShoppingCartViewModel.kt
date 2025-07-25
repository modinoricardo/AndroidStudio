package com.example.proyectofinalricardomitienda.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalricardomitienda.entities.ShoppingCartProduct
import com.example.proyectofinalricardomitienda.exceptions.NoStockException
import com.example.proyectofinalricardomitienda.exceptions.NegativeNumberException
import com.example.proyectofinalricardomitienda.state.MainState
import kotlinx.coroutines.launch

class ShoppingCartViewModel : ViewModel() {

    private val state = MainState()

    private val _datos = MutableLiveData<ShoppingCartProduct>(
        ShoppingCartProduct(arrayListOf(), 0.0)
    )
    val datos: LiveData<ShoppingCartProduct> = _datos

    private val _addToCartResult = MutableLiveData<ShoppingCartProduct>()
    val addToCartResult: LiveData<ShoppingCartProduct> = _addToCartResult

    private val _deleteFromCartResult = MutableLiveData<ShoppingCartProduct>()
    val deleteFromCartResult: LiveData<ShoppingCartProduct> = _deleteFromCartResult

    private val _cartError = MutableLiveData<String?>()
    val cartError: LiveData<String?> = _cartError

    init {
        // Carga inicial del carrito
        returnAllCart()
    }

    fun returnAllCart() {
        viewModelScope.launch {
            try {
                val cart = state.returnAllCart()
                _datos.value = cart
                _cartError.value = null
            } catch (e: Exception) {
                _cartError.value = e.message
            }
        }
    }

    fun addProductToCart(productId: Long, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                val response = if (quantity > 1) {
                    state.addProductToCart(productId, quantity)
                } else {
                    state.addProductToCart(productId)
                }
                _addToCartResult.value = response
                _datos.value = response
                _cartError.value = null
            } catch (e: NegativeNumberException) {
                _cartError.value = e.message
            } catch (e: NoStockException) {
                _cartError.value = e.message
            } catch (e: Exception) {
                _cartError.value = e.message
            }
        }
    }

    fun deleteProductFromCart(productId: Long) {
        viewModelScope.launch {
            try {
                val response = state.deleteOneProductFromCart(productId)
                _deleteFromCartResult.value = response
                _datos.value = response
                _cartError.value = null
            } catch (e: Exception) {
                _cartError.value = e.message
            }
        }
    }

    fun deleteAllProductsFromCart() {
        viewModelScope.launch {
            try {
                val response = state.deleteAllProductsFromCart()
                _deleteFromCartResult.value = response
                _datos.value = response
                _cartError.value = null
            } catch (e: Exception) {
                _cartError.value = e.message
            }
        }
    }
}
