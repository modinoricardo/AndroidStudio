package com.example.recyclerviewanimals.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewanimals.model.MainState
import com.example.recyclerviewanimals.model.MyData
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _datos= MutableLiveData<List<String>>(emptyList())
    val datos: LiveData<List<String>> get()=_datos
    val myEstado=MainState()

    private val _delete: MutableLiveData<MyData> = MutableLiveData<MyData>(MyData(0, emptyList<String>()))
    val delete: LiveData<MyData> get() = _delete

    private val _anyadir: MutableLiveData<MyData> = MutableLiveData<MyData>(MyData(0, emptyList<String>()))
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

    fun anyadir(position:Int, name:String){
        viewModelScope.launch {
            _anyadir.value = myEstado.anyadir(position, name)
        }
    }

}