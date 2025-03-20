package com.example.practicaut41_recyclerview_1.viewModel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaut41_recyclerview_1.model.ColorR
import com.example.practicaut41_recyclerview_1.model.MainState
import com.example.practicaut41_recyclerview_1.model.MyData
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _datos= MutableLiveData<List<ColorR>>(emptyList())
    val datos: LiveData<List<ColorR>> get()=_datos
    val myEstado= MainState()

    private val _delete: MutableLiveData<MyData> = MutableLiveData<MyData>(MyData(0, emptyList<ColorR>()))
    val delete: LiveData<MyData> get() = _delete

    private val _anyadir: MutableLiveData<MyData> = MutableLiveData<MyData>(MyData(0, emptyList<ColorR>()))
    val anyadir: LiveData<MyData> get() = _anyadir

    fun devuelveArray(){
        viewModelScope.launch {
            var retornoDatos=myEstado.devuelveArray()
            _datos.value = retornoDatos
        }
    }

    fun delete(myData: Int){
        viewModelScope.launch {
            _delete.value = myEstado.delete(myData)
        }
    }

    fun anyadir(position:Int, colores:ColorR){
        viewModelScope.launch {
            _anyadir.value = myEstado.anyadir(position, colores)
        }
    }

}