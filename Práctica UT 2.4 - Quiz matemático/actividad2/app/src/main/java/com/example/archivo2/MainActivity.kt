package com.example.archivo2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var numAleatorio = 0;

    lateinit var botonGeneraNumAleatorio: Button
    lateinit var textVisualizaNumAleatorio: TextView

    lateinit var comprobarResultado: Button
    lateinit var boxDivisibleDos: CheckBox
    lateinit var boxDivisibleTres: CheckBox
    lateinit var boxDivisibleCinco: CheckBox
    lateinit var boxDivisibleDiez: CheckBox
    lateinit var boxNoDivisible: CheckBox
    lateinit var respuesta: TextView
    lateinit var imagen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        botonGeneraNumAleatorio = findViewById(R.id.button)
        textVisualizaNumAleatorio = findViewById(R.id.textView2)
        comprobarResultado = findViewById(R.id.button3)

        boxDivisibleDos = findViewById(R.id.checkBox6)
        boxDivisibleTres = findViewById(R.id.checkBox7)
        boxDivisibleCinco = findViewById(R.id.checkBox8)
        boxDivisibleDiez = findViewById(R.id.checkBox9)
        boxNoDivisible = findViewById(R.id.checkBox10)
        respuesta = findViewById(R.id.textView4)
        imagen = findViewById(R.id.imageView)

        imagen.setImageResource(R.drawable.pantallablanca)

    }

    fun generarNumAleatorio(v: View) {
        numAleatorio = (1000..2000).random()
        textVisualizaNumAleatorio.text = numAleatorio.toString()
        imagen.setImageResource(R.drawable.pantallablanca)
        respuesta.text = ""

        boxDivisibleDos.isChecked = false
        boxDivisibleTres.isChecked = false
        boxDivisibleCinco.isChecked = false
        boxDivisibleDiez.isChecked = false
        boxNoDivisible.isChecked = false


    }

    fun comprobarResultado(v: View) {
        if (!algunaOpcionSeleccionada()) {
            respuesta.text = "Debe escoger al menos una de las opciones"
            imagen.setImageResource(R.drawable.pantallablanca)
        } else {

            if (opcionSeleccionada()) {
                respuesta.text = "Correcto"
                respuesta.setTextColor(ContextCompat.getColor(this, R.color.green))
                imagen.setImageResource(R.drawable.ok)
            } else {
                respuesta.text = "Error"
                respuesta.setTextColor(ContextCompat.getColor(this, R.color.red))
                imagen.setImageResource(R.drawable.ko)
            }
        }
    }

    fun opcionSeleccionada(): Boolean {

        var esDivisible = true

        if (boxDivisibleDos.isChecked) {

            esDivisible = esDivisible(2)
            if (!esDivisible) return false

        } else if (boxDivisibleTres.isChecked) {

            esDivisible = esDivisible(3)
            if (!esDivisible) return false

        } else if (boxDivisibleCinco.isChecked) {

            esDivisible = esDivisible(5)
            if (!esDivisible) return false

        } else if (boxDivisibleDiez.isChecked) {

            esDivisible = esDivisible(10)
            if (!esDivisible) return false

        } else if (boxNoDivisible.isChecked) {

            var res = textVisualizaNumAleatorio.text.toString().toInt()
            if (res % 2 != 0 &&
                res % 3 != 0 &&
                res % 4 != 0 &&
                res % 10 != 0
            ) {
                return true
            } else {
                return false
            }

        }
        return esDivisible
    }

    fun esDivisible(num: Int): Boolean {
        var res = textVisualizaNumAleatorio.text.toString().toInt() % num

        if (res == 0) {
            return true
        }
        return false
    }

    fun algunaOpcionSeleccionada(): Boolean {
        return boxDivisibleDos.isChecked || boxDivisibleDiez.isChecked || boxNoDivisible.isChecked || boxDivisibleTres.isChecked || boxDivisibleCinco.isChecked
    }

}