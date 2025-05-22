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

    private val _paginaActual = MutableLiveData(1)
    val paginaActual: LiveData<Int> = _paginaActual

    private val _totalPaginas = MutableLiveData(1)
    val totalPaginas: LiveData<Int> = _totalPaginas

    private var categoriaSeleccionadaId: Long? = null
    private var textoBuscado: String? = null

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun cargarProductos(textoBuscado:String?, categoriaSeleccionadaId:Long?,paginaActual:Int, tamanio:Int) {
        viewModelScope.launch {
            try {
                val result = mainState.recuperarProductos(textoBuscado, categoriaSeleccionadaId, paginaActual, tamanio)
                result?.let {
                    _productos.value = it
                    _totalPaginas.value = it.totalPages
                    _paginaActual.value = it.number + 1
                }
            } catch (_: Exception) {
                _productos.value = ResponseProduct()
            }
        }
    }

    fun filtrarProductos(search: String?, categoriaId: Long?) {
        this.textoBuscado = search
        this.categoriaSeleccionadaId = categoriaId
        _paginaActual.value = 1
//        cargarProductos()
    }


    fun cargarCategorias() {
        viewModelScope.launch {
            try {
                _categorias.value = mainState.getAllCategories()
            } catch (_: Exception) {}
        }
    }

    fun seleccionarCategoria(posicion: Int) {
        val listaCategorias = _categorias.value ?: return
        categoriaSeleccionadaId = listaCategorias[posicion].id
        _paginaActual.value = 0
//        cargarProductos()
    }

    fun paginaSiguiente() {
        if ((_paginaActual.value ?: 0) < (_totalPaginas.value ?: 1) - 1) {
            _paginaActual.value = (_paginaActual.value ?: 0) + 1
//            cargarProductos()
        }
    }

}
