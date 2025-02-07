package com.example.pruebamensajes

import android.content.DialogInterface
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var texto: TextView
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var miVista:View
    lateinit var miImagen:ImageView
    lateinit var animacion:AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        texto = findViewById(R.id.textView)
        coordinatorLayout=findViewById((R.id.coordinador))
        miImagen = findViewById(R.id.imageView)
        /*Glide.with(this).load(R.drawable.dado_imagen_animada_0092).into(miImagen)*/

        miImagen.setBackgroundResource(R.drawable.tragaperras)
        animacion = miImagen.background as AnimationDrawable

    }

    fun toast(v: View) {
//        var miToast: Toast = Toast.makeText(this, "hola", Toast.LENGTH_LONG)
//        miToast.show()
        animacion.start()
    }

    fun snackBar(v: View) {
//        Snackbar.make(v, "Adios", Snackbar.LENGTH_SHORT)
//            .setAction("accion", View.OnClickListener { texto.text="Accion de SnackBar" })
//            .show()
        animacion.stop()
    }

    fun alert(v:View){
        var myAlert = AlertDialog.Builder(this)
        myAlert.setTitle("Alerta")
        myAlert.setMessage("Esta es una alerta")
        myAlert.setPositiveButton("OK", DialogInterface.OnClickListener({
            dialog, which -> texto.text="Has pulsado OK"
        }))
        myAlert.setNegativeButton("Cancelar", DialogInterface.OnClickListener({
            _, _->this.finish()
        }))
        myAlert.setNeutralButton("Esto es un boton mas largo y se saleeeeeeeeeeeeeeeeeeeeeeeeeeee", null)
        myAlert.create().show()
    }

    fun customAlert(v: View){
        var myAlert = AlertDialog.Builder(this)
        myAlert.setTitle("Alert")

        miVista = layoutInflater.inflate(R.layout.loggin, null)
        myAlert.setView(miVista)
        myAlert.setPositiveButton("OK", DialogInterface.OnClickListener({
            _,_-> texto.text = miVista.findViewById<EditText>(R.id.username).text
        }))
        myAlert.setNegativeButton("Cancelar", DialogInterface.OnClickListener({
            _,_->this.finish()
        }))
        myAlert.create().show()

    }

}