package com.example.practicaut41_recyclerview_1

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaut41_recyclerview_1.databinding.ActivityMainBinding
import com.example.practicaut41_recyclerview_1.model.ColorR
import com.example.practicaut41_recyclerview_1.viewModel.MainViewModel

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
            myViewModel.devuelveArray()
            val mLayout= LinearLayoutManager(this@MainActivity)
            rvColores.layoutManager= mLayout

            myViewModel.datos.observe(this@MainActivity){
                myAdapter= MyAdapter(it)
                rvColores.adapter=myAdapter
                val miDividerItemDeclaration=
                    DividerItemDecoration(
                        rvColores.context, mLayout.orientation
                    )
                rvColores.addItemDecoration(miDividerItemDeclaration)
            }

            botonDelete.setOnClickListener{
                if(myAdapter.clickPosition < 0){
                    Toast.makeText(application, "Debe seleccionar una fila", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                myViewModel.delete(myAdapter.clickPosition)
            }

            myViewModel.delete.observe(this@MainActivity){
                myAdapter.notifyItemRemoved(it.position)
                myAdapter.clickPosition = RecyclerView.NO_POSITION
                myAdapter.notifyItemRangeChanged(0, it.listaColores.size)
                rvColores.scheduleLayoutAnimation()
            }

            botonAnyadir.setOnClickListener{
                var position = if (myAdapter.clickPosition < 0) 0 else myAdapter.clickPosition

                val nombreColor = editTextText.text.toString().trim()
                val idColor = editTextIdColor.text.toString().trim()

                if(nombreColor.isEmpty() || idColor.isEmpty()){
                    Toast.makeText(application, "Por favor, rellene ambos campos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                myViewModel.anyadir(position, ColorR(editTextText.text.toString(), editTextIdColor.text.toString()))
            }

            myViewModel.anyadir.observe(this@MainActivity){
                myAdapter.notifyItemRemoved(it.position)
                myAdapter.clickPosition = RecyclerView.NO_POSITION
                myAdapter.notifyItemRangeChanged(0, it.listaColores.size)
                rvColores.scheduleLayoutAnimation()
            }

        }

    }
}