package com.example.proyectofinalricardomitienda

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalricardomitienda.entities.TokenResponse
import com.example.proyectofinalricardomitienda.model.MainState
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    val mainState = MainState()

    private val _code = MutableLiveData<Int>()
    val code : LiveData<Int> get()= _code

    fun login(user: String, password: String) {
        viewModelScope.launch {
            _code.value=mainState.login(user,password)
        }
    }

}