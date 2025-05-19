package com.example.proyectofinalricardomitienda.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalricardomitienda.entities.Product
import com.example.proyectofinalricardomitienda.entities.ResponseProduct
import com.example.proyectofinalricardomitienda.pagination.DatosPaginacion
import com.example.proyectofinalricardomitienda.state.MainState
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val state = MainState()
    private val pagination = DatosPaginacion()      // páginaActual = 1
    private var _isLoading = false

    // FILTER STATE
    private var currentSearch: String? = null
    private var currentCategory: Long? = null

    // Acumula productos
    private val _items = MutableLiveData<MutableList<Product>>(mutableListOf())
    val items: LiveData<MutableList<Product>> = _items

    // Exponer loading para el fragment
    val isLoading: Boolean get() = _isLoading

    init {
        refresh()  // carga sin filtro
    }

    /** Reinicia búsqueda/categoría y carga la página 1 */
    fun refresh() {
        currentSearch = null
        currentCategory = null
        pagination.paginaActual = 1
        _items.value = mutableListOf()
        loadNextPage()
    }

    fun returnAllProductsByName(search: String) {
        currentSearch = search
        currentCategory = null
        pagination.paginaActual = 1
        _items.value = mutableListOf()
        loadNextPage()
    }

    fun returnAllProductsByCategory(cat: Long) {
        currentSearch = null
        currentCategory = cat
        pagination.paginaActual = 1
        _items.value = mutableListOf()
        loadNextPage()
    }

    fun returnAllProductsByAll(search: String, cat: Long) {
        currentSearch = search
        currentCategory = cat
        pagination.paginaActual = 1
        _items.value = mutableListOf()
        loadNextPage()
    }

    /**
     * Según el filtro activo, llama al endpoint paginado
     * correcto y acumula la lista.
     */
    fun loadNextPage() {
        if (_isLoading) return
        _isLoading = true

        viewModelScope.launch {
            // 1) Elegimos el response según filtro
            val response = when {
                currentSearch != null && currentCategory != null ->
                    state.returnAllProductsByAllPaged(currentSearch!!, currentCategory!!, pagination.paginaActual)
                currentSearch != null ->
                    state.returnAllProductsByNamePaged(currentSearch!!, pagination.paginaActual)
                currentCategory != null ->
                    state.returnAllProductsByCategoryPaged(currentCategory!!, pagination.paginaActual)
                else ->
                    state.returnAllProductsPaged(pagination.paginaActual)
            }

            // 2) Acumulamos
            val current = _items.value ?: mutableListOf()
            current.addAll(response.content)
            _items.postValue(current)

            // 3) Avanzamos la página
            pagination.paginaActual++
            _isLoading = false
        }
    }
}
