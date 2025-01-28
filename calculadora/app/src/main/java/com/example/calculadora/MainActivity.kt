package com.example.calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var num1:TextView
    private lateinit var num2:TextView

    private lateinit var resultado:TextView

    private lateinit var botonSumar:Button
    private lateinit var botonRestar:Button
    private lateinit var botonMultiplicar:Button
    private lateinit var botonDivision:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        num1 = findViewById(R.id.editTextNumber)
        num2 = findViewById(R.id.editTextNumber2)

        resultado = findViewById(R.id.textView5)

        botonSumar = findViewById(R.id.buttonSuma)
        botonRestar = findViewById(R.id.buttonResta)
        botonMultiplicar = findViewById(R.id.buttonMultiplicacion)
        botonDivision = findViewById(R.id.buttonDivision)

        botonSumar.setOnClickListener(){
            var valor1 = num1.text.toString().toDouble()
            var valor2 = num2.text.toString().toDouble()
                val suma = valor1 + valor2
                resultado.text = suma.toString()
        }

        botonRestar.setOnClickListener{
            var valor1 = num1.text.toString().toDouble()
            var valor2 = num2.text.toString().toDouble()
                val suma = valor1 - valor2
                resultado.text = suma.toString()
        }

        botonMultiplicar.setOnClickListener{
            var valor1 = num1.text.toString().toDouble()
            var valor2 = num2.text.toString().toDouble()

                val suma = valor1 * valor2
                resultado.text = suma.toString()
        }

        botonDivision.setOnClickListener{
            var valor1 = num1.text.toString().toDouble()
            var valor2 = num2.text.toString().toDouble()

            val suma = valor1 / valor2
            resultado.text = suma.toString()
        }


    }

}