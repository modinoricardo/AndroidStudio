package com.example.recyclerviewanimals

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewanimals.databinding.ActivityMainBinding
import com.example.recyclerviewanimals.viewModel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val myViewModel: MainViewModel by viewModels()
    lateinit var myAdapter: MyAdapter

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
            myViewModel.devuelveArray()
            val mLayout = LinearLayoutManager(this@MainActivity)
            rvAnimales.layoutManager = mLayout

            myViewModel.datos.observe
//            lifecycleScope.launch{
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    myAdapter = MyAdapter(it)
                    rvAnimales.adapter = myAdapter
                }
//            }

        }

    }
}