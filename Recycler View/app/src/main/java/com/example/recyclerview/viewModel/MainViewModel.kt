package com.example.recyclerview.viewModel

import androidx.lifecycle.ViewModel
import com.example.recyclerview.model.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _datos = MutableStateFlow<List<String>>(emptyList())
    val datos : StateFlow<List<String>> get() = _datos.asStateFlow()
    val myEstado = MainState();

    fun devuelveArray() {
        viewModelScope.launch {

        }
    }
}