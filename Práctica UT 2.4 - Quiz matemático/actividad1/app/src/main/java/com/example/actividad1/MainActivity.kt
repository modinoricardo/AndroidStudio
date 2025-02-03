package com.example.actividad1

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var botonGenNumAleatorio: Button
    lateinit var textMuestraNumAleatorio:TextView
    lateinit var radioBotonSi:RadioButton
    lateinit var radioBotonNo:RadioButton
    lateinit var botonComprobarAnyo:Button
    lateinit var textComprobarAnyo:TextView

    lateinit var switch: Switch
    lateinit var main:ConstraintLayout

    var numAleatorio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        botonGenNumAleatorio = findViewById(R.id.button)
        textMuestraNumAleatorio = findViewById(R.id.textView)

        botonComprobarAnyo = findViewById<Button>(R.id.button2)
        textComprobarAnyo = findViewById<TextView>(R.id.textView4)
        radioBotonSi = findViewById<RadioButton>(R.id.radioButton)
        radioBotonNo = findViewById<RadioButton>(R.id.radioButton2)

        main = findViewById(R.id.main)
        switch = findViewById(R.id.switch1)

        botonGenNumAleatorio.setOnClickListener{
            numAleatorio = (1900..2500).random()
            textMuestraNumAleatorio.text = numAleatorio.toString()
        }



    }

    fun cambiarColorFondo(v:View){
        if(switch.isChecked){
            switch.text = "Cambiar fondo blanco"
            main.setBackgroundColor(Color.YELLOW)
        }else{
            switch.text = "Cambiar fondo amarillo"
            main.setBackgroundColor(Color.WHITE)
        }
    }

    fun comprobarAnyo(v:View){
        if(radioBotonSi.isChecked || radioBotonNo.isChecked){
            if(radioBotonSi.isChecked){
                if(numAleatorio%4==0){
                    textComprobarAnyo.text = "Correcto"
                    textComprobarAnyo.setTextColor(Color.GREEN)
                }else{
                    textComprobarAnyo.text = "Error"
                    textComprobarAnyo.setTextColor(Color.RED)
                }
            }else{
                if(numAleatorio%4==0){
                    textComprobarAnyo.text = "Error"
                    textComprobarAnyo.setTextColor(Color.RED)
                }else{
                    textComprobarAnyo.text = "Correcto"
                    textComprobarAnyo.setTextColor(Color.GREEN)
                }
            }

        }else{
            textComprobarAnyo.text = "Debe escoger una de las opciones"
            textComprobarAnyo.setTextColor(Color.BLUE)
        }
    }

}