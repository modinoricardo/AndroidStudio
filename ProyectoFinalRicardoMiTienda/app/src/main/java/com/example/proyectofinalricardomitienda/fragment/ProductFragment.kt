package com.example.proyectofinalricardomitienda.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalricardomitienda.adapter.ProductAdapter
import com.example.proyectofinalricardomitienda.databinding.FragmentProductBinding
import com.example.proyectofinalricardomitienda.model.ProductViewModel
import com.google.android.material.snackbar.Snackbar

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private lateinit var layoutManager: LinearLayoutManager

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

        adapter = ProductAdapter()
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.layoutManager = layoutManager
        binding.rvProducts.adapter = adapter

        viewModel.productos.observe(viewLifecycleOwner) { productList ->
            productList?.let {
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
                            viewModel.paginaSiguiente()
                        }
                        .show()
                }
            }
        })

        // Botón de búsqueda
        binding.btnPSearch.setOnClickListener {
            val texto = binding.txtPSearch.text.toString().takeIf { it.isNotBlank() }
            val catPos = binding.spinnerPCategory.selectedItemPosition.takeIf { it > 0 }?.toLong()
            viewModel.filtrarProductos(texto, catPos)
        }

        // Carga inicial del ViewModel, limpiamos all
        viewModel.cargarCategorias()
        viewModel.cargarProductos(null, 1, 1, 5)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}
