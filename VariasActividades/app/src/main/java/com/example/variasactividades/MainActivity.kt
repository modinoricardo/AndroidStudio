package com.example.variasactividades

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.variasactividades.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var myActivityResultLauncher: ActivityResultLauncher<Intent>

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

                myActivityResultLauncher = registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()){
//                    result: ActivityResult? ->
                    if(it!!.resultCode == Activity.RESULT_OK){
                        val miIntentResultado = it.data
                        findViewById<TextView>(R.id.textView2).text = miIntentResultado!!.extras!!.getString("mensajeBack")
                    }else{
                        Toast.makeText(this@MainActivity, "Ha fallado la vuelta", Toast.LENGTH_LONG).show()
                    }

                }
            button2.setOnClickListener{
                val myIntent = Intent(this@MainActivity, NuevaActivity::class.java)
                myIntent.putExtra("texto", editTextText.text.toString())
                myActivityResultLauncher.launch(myIntent)
            }


        }
    }
}