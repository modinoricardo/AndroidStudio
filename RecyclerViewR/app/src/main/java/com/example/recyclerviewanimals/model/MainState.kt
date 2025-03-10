package com.example.recyclerviewanimals.model

private var animalesName= mutableListOf("Caballo", "Vaca", "Perro", "Gato", "Leon", "Tigre", "Rinoceronte", "Elefante", "Aguila", "Mariposa", "Serpiente", "Oso")

class MainState {
    fun devuelveArray():List<String>{
        return animalesName
    }

    fun delete(data:Int):MyData{
        animalesName.removeAt(data)
        return MyData(data, animalesName)
    }

}