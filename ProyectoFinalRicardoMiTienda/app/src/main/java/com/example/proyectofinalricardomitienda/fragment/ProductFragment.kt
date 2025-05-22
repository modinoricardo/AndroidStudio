package com.example.proyectofinalricardomitienda.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.adapter.ProductAdapter
import com.example.proyectofinalricardomitienda.databinding.FragmentProductBinding
import com.example.proyectofinalricardomitienda.entities.Category
import com.example.proyectofinalricardomitienda.model.ProductViewModel
import com.google.android.material.snackbar.Snackbar

class ProductFragment : Fragment() , AdapterView.OnItemSelectedListener{

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var spinner: Spinner

    private var textoSeleccionado: String? = null
    private var categoriaSeleccionada: Long? = null
    private var paginaActual:Int = 1
    private var tamanio:Int = 5

    private var busqueda:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.spinnerPCategory)
        spinner.onItemSelectedListener = this

        adapter = ProductAdapter()
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.layoutManager = layoutManager
        binding.rvProducts.adapter = adapter

        viewModel.productos.observe(viewLifecycleOwner) { productList ->
            productList?.let {
                if(busqueda) {
                    adapter = ProductAdapter()
                    binding.rvProducts.adapter = adapter
                }
                    val lista = it.content
                    adapter.updateListProduct(lista)
            }
        }

        binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                if (dy <= 0) return // solo scroll hacia abajo

                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val totalItems = adapter.itemCount

                if (lastVisible >= totalItems - 1) {
                    Snackbar.make(rv, "¿Cargar más productos?", Snackbar.LENGTH_LONG)
                        .setAction("Sí") {
//                            viewModel.paginaSiguiente()
                        }
                        .show()
                }
            }
        })

        // Botón de búsqueda
        binding.btnPSearch.setOnClickListener {
            busqueda = true
            textoSeleccionado = binding.txtPSearch.text.toString().takeIf { it.isNotBlank() }
//            categoriaSeleccionada = binding.spinnerPCategory.selectedItemPosition.takeIf { it > 0 }?.toLong()
            viewModel.cargarProductos(textoSeleccionado, categoriaSeleccionada, paginaActual,tamanio)
        }

        // Carga inicial del ViewModel, limpiamos all
        viewModel.cargarCategorias()
        viewModel.cargarProductos(textoSeleccionado, categoriaSeleccionada, paginaActual, tamanio)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedCategory = parent?.getItemAtPosition(position) as String
        when(selectedCategory){
            "Sin seleccion" -> categoriaSeleccionada = null
            "Coffees" -> categoriaSeleccionada = 1
            "Teas" -> categoriaSeleccionada = 2
            "Cocktails" -> categoriaSeleccionada = 3
            "Pintxos" -> categoriaSeleccionada = 4
            "Desserts" -> categoriaSeleccionada = 5
        }

    }

    //Se deja tal cual
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
