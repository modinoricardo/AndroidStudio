package com.example.hilos

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hilos.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var btToast: Toast

    private  lateinit var binding: ActivityMainBinding
    private lateinit var coroutine:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            with(binding){
                btToast.setOnClickListener({toastHola("Hola")})

                btSinHilos.setOnClickListener{
                    pB1.max=100
                    pB1.progress = 0
                    for (i in 1..100){
                        Thread.sleep(1000)
                        pB1.progress=i+10
                    }
                    toastHola("Tarea finalizada")
                    myTV.text= "Tarea finalizada"
                }

                btConHilos.setOnClickListener{
                    coroutine=lifecycleScope.launch {
                        tareaLargaKotlin(pB1, myTV)
                    }
                    myTV.text= "Tarea en curso"
                }

                btStop.setOnClickListener({
                    coroutine.cancel()
                    myTV.text = "Tarea cancelada"
                })

            }

    }

    private fun toastHola(mensage:String){
        Toast.makeText(this, "Hola", Toast.LENGTH_LONG).show()
    }

    private fun tareaLarga(pB1: ProgressBar, myTV: TextView){
        pB1.max=100
        pB1.progress = 0
        for (i in 1..100){
            Thread.sleep(1000)
            pB1.progress=i*10
        }
        toastHola("Tarea finalizada")
        myTV.text= "Tarea finalizada"
    }

    suspend fun tareaLargaKotlin(pB1: ProgressBar, myTV: TextView){
        pB1.max=100
        pB1.progress = 0
        for (i in 1..10){
            delay(1000)
            pB1.progress=i*10
        }
        toastHola("Tarea finalizada")
        myTV.text= "Tarea finalizada"
    }

}