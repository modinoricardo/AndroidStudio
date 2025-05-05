package com.example.proyectofinalricardomitienda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinalricardomitienda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding){
//
//            myActivityResultLauncher = registerForActivityResult(
//                ActivityResultContracts.StartActivityForResult()){
//                   result ->
//                        if (result.resultCode == RESULT_OK) {
//                            val data = result.data
//                        }
//            }

            buttonStarSesion.setOnClickListener{
                val myIntent = Intent(this@MainActivity, PaginaPrincipal::class.java)
                startActivity(myIntent)
            }
        }
    }
}