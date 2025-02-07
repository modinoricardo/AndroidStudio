package com.example.ut2_spinner

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner
    lateinit var spinnerSecundario: Spinner
    lateinit var imagen: ImageView

    var temas = arrayOf("Serie", "Grupo música", "Anime")

    var series = arrayOf("Castle", "El mentalista", "Lucifer")
    var gruposMusica = arrayOf("Imagine Dragons", "The Beatles", "AC/DC")
    var anime = arrayOf("Boku no Hero", "Kimetsu no Yaiba", "Nanatsu no Taizai")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinner = findViewById(R.id.spinner)
        spinnerSecundario = findViewById(R.id.spinner2)
        imagen = findViewById(R.id.imageView2)

        val adapterPrincipal = ArrayAdapter(this, android.R.layout.simple_spinner_item, temas)
        adapterPrincipal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterPrincipal

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                actualizarSpinnerSecundario(temas[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerSecundario.visibility = View.INVISIBLE
            }
        }

        spinnerSecundario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val seleccion = spinnerSecundario.selectedItem.toString()
                actualizarImagen(seleccion)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun actualizarSpinnerSecundario(opcion: String) {
        val opcionesAMostrar = when (opcion) {
            "Serie" -> series
            "Grupo música" -> gruposMusica
            "Anime" -> anime
            else -> emptyArray()
        }

        if (opcionesAMostrar.isNotEmpty()) {
            spinnerSecundario.visibility = View.VISIBLE
            val adapterSecundario = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesAMostrar)
            adapterSecundario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSecundario.adapter = adapterSecundario
        } else {
            spinnerSecundario.visibility = View.INVISIBLE
        }
    }

    fun actualizarImagen(seleccion: String) {
        when (seleccion) {
            "Castle" -> imagen.setImageResource(R.drawable.scale)
            "El mentalista" -> imagen.setImageResource(R.drawable.elmentalista)
            "Lucifer" -> imagen.setImageResource(R.drawable.lucifer)

            "Imagine Dragons" -> imagen.setImageResource(R.drawable.imagindragons)
            "The Beatles" -> imagen.setImageResource(R.drawable.losbeatles)
            "AC/DC" -> imagen.setImageResource(R.drawable.acdc)

            "Boku no Hero" -> imagen.setImageResource(R.drawable.bokunopico)
            "Kimetsu no Yaiba" -> imagen.setImageResource(R.drawable.kimetsunojaiba)
            "Nanatsu no Taizai" -> imagen.setImageResource(R.drawable.nanatzunotaizai)

            else -> imagen.visibility = View.INVISIBLE
        }
    }
}
