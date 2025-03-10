package com.example.practicaut41_recyclerview_1.model

class ColorR(val nombre:String, val codigo:String)

private var colores:MutableList<ColorR> = mutableListOf()

class MainState {

    fun devuelveArray():List<ColorR>{
        return colores
    }

    val colorRojo = ColorR("Rojo", "#FF0000")
    val colorNaranja = ColorR("Naranja", "#FFA500")
    val colorAmarillo = ColorR("Amarillo", "#FFFF00")
    val colorVerde = ColorR("Verde","#77a345")
    val colorAzul= ColorR("Azul","#0000FF")
    val colorIndigo= ColorR("Indigo","#0000FF")
    val colorVioleta= ColorR("Violeta","#A020F0")

    init {
        for (i in 1..20){
            colores.add(colorRojo)
            colores.add(colorNaranja)
            colores.add(colorAmarillo)
            colores.add(colorVerde)
            colores.add(colorAzul)
            colores.add(colorIndigo)
            colores.add(colorVioleta)
        }
    }

}