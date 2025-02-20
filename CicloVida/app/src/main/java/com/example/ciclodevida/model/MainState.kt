package com.example.ciclodevida.model

class MainState {
    fun sumar(valor:Int, datos:Datos): Datos {
        datos.contador += valor
        datos.numClicks++

        if(datos.numClicks>=5){
            datos.mostrarMensaje = true
            datos.numClicks = 0
        }

        return datos
    }
}