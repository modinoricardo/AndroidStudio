package com.example.variasactividades

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NuevaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var myExtra : Bundle? = intent.extras
        var myMessage = myExtra!!.getString("texto")


        findViewById<Button>(R.id.button).setOnClickListener{
            val myResult = Intent()
            myResult.putExtra("mensajeBack", findViewById<TextView>(R.id.textView).text.toString())
            setResult(RESULT_OK, myResult)

            finish()
        }
        findViewById<TextView>(R.id.textView).text = myMessage
    }
}