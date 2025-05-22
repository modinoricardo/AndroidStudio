package com.example.proyectofinalricardomitienda.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalricardomitienda.entities.Category
import com.example.proyectofinalricardomitienda.entities.Product
import com.example.proyectofinalricardomitienda.entities.ResponseProduct
import com.example.proyectofinalricardomitienda.state.MainState
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val mainState = MainState()

    private val _productos = MutableLiveData<ResponseProduct>()
    val productos: LiveData<ResponseProduct> = _productos

    private val _categorias = MutableLiveData<List<Category>>()
    val categorias: LiveData<List<Category>> = _categorias

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun cargarProductos(textoBuscado:String?, categoriaSeleccionadaId:Long?,paginaActual:Int, tamanio:Int) {
        viewModelScope.launch {
            try {
                val result = mainState.recuperarProductos(textoBuscado, categoriaSeleccionadaId, paginaActual, tamanio)
                result?.let {
                    _productos.value = it
                }
            } catch (_: Exception) {
                _productos.value = ResponseProduct()
            }
        }
    }

    fun cargarCategorias() {
        viewModelScope.launch {
            try {
                _categorias.value = mainState.getAllCategories()
            } catch (_: Exception) {}
        }
    }

}
