package com.example.misfragmentos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btShow = findViewById<Button>(R.id.btLanzarFragmento)

        btShow.setOnClickListener{
            val editTextInput = findViewById<EditText>(R.id.textoEditable)

            val myFragmentManager : FragmentManager = supportFragmentManager
            val myFragmentTransaction: FragmentTransaction = myFragmentManager.beginTransaction()
            val myFragment: FirstFragment = FirstFragment.newInstance(editTextInput.text.toString())

            myFragmentTransaction.replace(R.id.frameLayout, myFragment)
            myFragmentTransaction.commit()
        }

    }
}