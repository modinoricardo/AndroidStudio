package com.example.recyclerview.model

class MainState {

    fun devolverArray() : List<String> {
        var animales = mutableListOf("Caballo", "Vaca", "Toro", "Gaviota", "Perro", "Cabra", "Gacela", "Topo"
            "Merluza", "Chipiron", "Abeja", "Coyote", "Oso");

        return animales;
    }
}