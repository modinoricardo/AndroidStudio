package com.example.jugandoalosdados

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SegundaActivity : AppCompatActivity() {

    lateinit var imageViewUp:ImageView
    lateinit var imageViewDown:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_segunda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageViewUp = findViewById(R.id.imageView3)
        imageViewDown = findViewById(R.id.imageView4)

        Glide.with(this).load(R.drawable.nomoney).into(imageViewUp)
        Glide.with(this).load(R.drawable.nomoney).into(imageViewDown)

        lifecycleScope.launch {
            delay(500)
            aviso()
        }
    }

    private fun aviso() {
        val titulo = "Jugando a los Dados"
        val mensaje = """
            ¡Gracias por participar!
            Lamentablemente en estos momentos su saldo esta a 0, 
            lo que significa un adios por ahora, 
            espero que vuelva a abrir la app "Jugando a los dados"
            para seguir probando suerte 
            
            ¡Muchas gracias por ser participe de la experiencia!
            Realizado por Ricardo Modino
        """.trimIndent()
        val myAlert = AlertDialog.Builder(this)

        myAlert.setTitle(titulo)
        var menAviso = "Por si no te habias dado cuenta has"
        myAlert.setMessage("$mensaje\n\n$menAviso ganado esta ronda ;) \n ¿Subimos la apuesta?")
        myAlert.setPositiveButton("Salir del juego", DialogInterface.OnClickListener({
                _, _->this.finish()
        }))
        myAlert.create().show()
    }
}