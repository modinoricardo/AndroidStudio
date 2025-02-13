package com.example.hilos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hilos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var btToast: Toast

    private  lateinit var binding: ActivityMainBinding

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
                        tareaLarga()
                        pB1.progress=i+10
                    }
                    toastHola("Tarea finalizada")
                    myTV.text= "Tarea finalizada"
                }
            }

    }

    private fun toastHola(mensage:String){
        Toast.makeText(this, "Hola", Toast.LENGTH_LONG).show()
    }

    private fun tareaLarga(){
        Thread.sleep(1000)

    }

}