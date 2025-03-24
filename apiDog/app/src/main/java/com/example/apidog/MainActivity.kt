package com.example.apidog

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apidog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myViewModel: MainViewModel by viewModels()
    lateinit var myAdapter: MyAdapter

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
            val mLayout = LinearLayoutManager(this@MainActivity)
            rvPerros.layoutManager = mLayout

            bt2.setOnClickListener{
                val raza = edt2.getText().toString()
                if(raza.isEmpty()){
                    Toast.makeText(applicationContext, "Debe seleecionar una raza", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                myViewModel.devuelveFotos(raza)
            }

            myViewModel.datos.observe(this@MainActivity){
                if(it.status=="success"){
                    myAdapter = MyAdapter(it)
                    rvPerros.adapter = myAdapter
                }else{
                    Toast.makeText(applicationContext, "No hay fotos de esa raza", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }


    }
}