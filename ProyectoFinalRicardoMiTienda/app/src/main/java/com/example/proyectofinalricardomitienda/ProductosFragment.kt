package com.example.proyectofinalricardomitienda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductosFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var adapter: ProductoAdapter
    private var productos: List<Producto> = listOf()
    private var categorias: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos, container, false)
        spinner = view.findViewById(R.id.spinner_categoria)
        recyclerView = view.findViewById(R.id.recycler_productos)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ProductoAdapter(listOf())
        recyclerView.adapter = adapter

        cargarCategorias()
        return view
    }

    private fun cargarCategorias() {
        // Simulación: podrías traer esto desde tu API
        categorias = listOf("Todos", "Camisetas", "Pantalones", "Zapatos")
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val categoria = categorias[position]
                cargarProductos(categoria)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun cargarProductos(categoria: String) {
        val url = if (categoria == "Todos") {
            "http://tuapi.com/api/productos"
        } else {
            "http://tuapi.com/api/productos?categoria=${categoria}"
        }

        // Usa Volley, Retrofit, o HttpUrlConnection (aquí ejemplo básico con Volley)
        val queue = Volley.newRequestQueue(requireContext())
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            productos = (0 until response.length()).map { i ->
                val obj = response.getJSONObject(i)
                Producto(
                    nombre = obj.getString("nombre"),
                    precio = obj.getDouble("precio"),
                    imagen = obj.getString("imagen")
                )
            }
            adapter.actualizarLista(productos)
        }, {
            Toast.makeText(context, "Error al cargar productos", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)
    }
}
