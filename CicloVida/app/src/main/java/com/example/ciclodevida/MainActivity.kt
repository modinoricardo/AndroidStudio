package com.example.ciclodevida

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ciclodevida.databinding.ActivityMainBinding
import com.example.ciclodevida.viewModel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val myViewModel:MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    var numClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.textView.text = "0"
//        binding.button.setOnClickListener({
//            binding.textView.text = (binding.textView.text.toString().toInt() +
//                    binding.editTextNumber.text.toString().toInt()).toString()
//                numClicks++
//                if(numClicks==5){
//                    Toast.makeText(this, "Hemos llegado a 5 clicks", Toast.LENGTH_SHORT).show()
//                    numClicks=0
//                }
//
//        })

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.datos.collect{
                    binding.textView.text = it.contador.toString()
                    numClicks = it.numClicks
                    if(it.mostrarMensaje == true){
                        Toast.makeText(applicationContext,"Has llegado a 5 clicks",
                        Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

//        binding.button.setOnClickListener({
//            myViewModel.sumar(binding.editTextNumber.text)
//        })
//
//        myViewModel.datos.observe(this){
//            binding.textView.text
//        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("contador", binding.textView.text.toString())
        outState.putInt("numClicks", numClicks)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        numClicks = savedInstanceState.getInt("numClicks")
        binding.textView.text = savedInstanceState.getString("contador")
    }

}