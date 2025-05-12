package com.example.proyectofinalricardomitienda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalricardomitienda.model.MainState
import kotlinx.coroutines.launch

class ShoppingCartViewModel : ViewModel() {
    val myEstado = MainState()
    private val _datos = MutableLiveData<ShoppingCartProduct>(ShoppingCartProduct())
    val datos: LiveData<ShoppingCartProduct> get() = _datos
    private val _addToCartResult = MutableLiveData<ShoppingCartProduct>()
    val addToCartResult: LiveData<ShoppingCartProduct> get() = _addToCartResult
    private val _deleteFromCartResult = MutableLiveData<ShoppingCartProduct>()
    val deleteFromCartResult: LiveData<ShoppingCartProduct> get() = _deleteFromCartResult

    fun returnAllCart() {
        viewModelScope.launch {
            _datos.value = myEstado.returnAllCart()
        }
    }

    fun addProductToCart(productId: Long, quantity: Int = 1) {
        viewModelScope.launch {
            if (quantity > 1) {
                val response = myEstado.addManyProductToCart(productId, quantity)
                _addToCartResult.value = response
            } else {
                val response = myEstado.addProductToCart(productId)
                _addToCartResult.value = response
            }

        }
    }

    fun deleteProductFromCart(position : Int) {
        viewModelScope.launch {
            val shoppingCartProductId= _datos.value!!.shoppingCartProducts[position].id
            val response = myEstado.deleteOneProductFromCart(shoppingCartProductId)
            _deleteFromCartResult.value = response

        }
    }

}