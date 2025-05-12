package com.example.proyectofinalricardomitienda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalricardomitienda.model.MainState
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    val myEstado= MainState()
    private val _datos= MutableLiveData<ResponseProduct>(ResponseProduct())
    val datos: LiveData<ResponseProduct> get()= _datos

    fun returnAllProducts(){
        viewModelScope.launch {
            _datos.value= myEstado.returnAllProducts()
        }
    }

    fun returnALlProductsByName(search:String){
        viewModelScope.launch {
            _datos.value= myEstado.returnAllProductsByName(search)
        }
    }

    fun returnAllProductsByCategory(cat: Long){
        viewModelScope.launch {
            _datos.value= myEstado.returnAllProductsByCategory(cat)
        }
    }

    fun returnAllProductsByAll(search: String, cat: Long){
        viewModelScope.launch {
            _datos.value= myEstado.returnAllProductsByAll(search, cat)
        }
    }
}