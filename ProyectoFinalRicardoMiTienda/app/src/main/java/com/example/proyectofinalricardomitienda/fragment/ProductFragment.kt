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

    // Para llevar la cuenta de cuántos ítems había antes
    private var previousSize = 0

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

        // 1) Inicializamos RecyclerView y Adapter (vacío)
        layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter(mutableListOf())
        binding.rvProducts.layoutManager = layoutManager
        binding.rvProducts.adapter = adapter

        // 2) Scroll listener para cargar la siguiente página
        val infiniteScroll = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                if (dy <= 0) return  // solo scroll hacia abajo

                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val totalItems  = adapter.itemCount
                if (lastVisible >= totalItems - 1 && !viewModel.isLoading) {
                    Snackbar.make(rv, "¿Cargar más productos?", Snackbar.LENGTH_LONG)
                        .setAction("Sí") {
                            viewModel.loadNextPage()
                        }
                        .show()
                }
            }
        }
        binding.rvProducts.addOnScrollListener(infiniteScroll)

        // 3) Observamos el LiveData de productos acumulados
        viewModel.items.observe(viewLifecycleOwner) { fullList ->
            if (previousSize == 0) {
                // Primera carga o tras buscar: reemplaza toda la lista
                adapter.replaceAll(fullList)
            } else {
                // Páginas siguientes: solo añade los nuevos
                val nuevos = fullList.subList(previousSize, fullList.size)
                adapter.addProducts(nuevos)
            }
            previousSize = fullList.size
        }

        // 4) Configuramos el botón de búsqueda
        binding.btnPSearch.setOnClickListener {
            // a) Reiniciamos el contador y limpiamos el adapter
            previousSize = 0
            adapter.replaceAll(emptyList())

            // b) Quitamos y volvemos a poner el scroll listener
            binding.rvProducts.clearOnScrollListeners()
            binding.rvProducts.addOnScrollListener(infiniteScroll)

            // c) Leemos filtro y lanzamos la petición adecuada
            val texto = binding.txtPSearch.text.toString()
            val catPos = binding.spinnerPCategory.selectedItemPosition.toLong()
            when {
                texto.isBlank() && catPos == 0L ->
                    viewModel.refresh()                         // Sin filtro
                texto.isBlank() ->
                    viewModel.returnAllProductsByCategory(catPos)
                catPos == 0L ->
                    viewModel.returnAllProductsByName(texto)
                else ->
                    viewModel.returnAllProductsByAll(texto, catPos)
            }
        }

        // 5) Primera carga al entrar al fragment
        previousSize = 0
        adapter.replaceAll(emptyList())
        viewModel.refresh()
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
