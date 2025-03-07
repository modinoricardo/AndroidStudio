package com.example.practicaut41_recyclerview_1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaut41_recyclerview_1.model.ColorR
import com.example.practicaut41_recyclerview_1.model.MainState
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val _datos= MutableLiveData<List<ColorR>>(emptyList())
    val datos: LiveData<List<ColorR>> get()=_datos
    val myEstado= MainState()

    fun devuelveArray(){
        viewModelScope.launch {
            var retornoDatos=myEstado.devuelveArray()
            _datos.value = retornoDatos
        }
    }

}