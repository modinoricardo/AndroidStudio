package com.example.recyclerviewanimals.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewanimals.model.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _datos= MutableStateFlow<List<String>>(emptyList())
    val datos: StateFlow<List<String>> get()=_datos.asStateFlow()
    val myEstado=MainState()

    fun devuelveArray(){
        viewModelScope.launch {
            var retornoDatos=myEstado.devuelveArray()
            _datos.value = retornoDatos
        }
    }
}