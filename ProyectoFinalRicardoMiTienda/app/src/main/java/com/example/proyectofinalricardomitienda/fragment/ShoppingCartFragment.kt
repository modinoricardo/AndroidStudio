package com.example.proyectofinalricardomitienda.fragment

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.entities.ShoppingCartProduct
import com.example.proyectofinalricardomitienda.adapter.ShoppingCartAdapter
import com.example.proyectofinalricardomitienda.databinding.FragmentShoppingCartBinding
import com.example.proyectofinalricardomitienda.model.ShoppingCartViewModel
import com.example.proyectofinalricardomitienda.util.Util

class ShoppingCartFragment : Fragment() {

    private val viewModel: ShoppingCartViewModel by viewModels()
    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var myAdapter: ShoppingCartAdapter

//    val pos = myAdapter.positionClicked
//    val cartItem = myAdapter.getItem(pos)
//    val productName = cartItem?.productName ?: "este producto"

    companion object {
        fun newInstance() = ShoppingCartFragment()
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val cartItem = myAdapter.getItem(position) ?: return
        val productId = cartItem.productId

        AlertDialog.Builder(requireContext())
            .setTitle("Borrar producto")
            .setMessage("¿Seguro que quieres borrar \"${cartItem.productName}\"?")
            .setPositiveButton("Sí") { _, _ ->
                viewModel.deleteProductFromCart(productId)
                myAdapter.positionClicked = RecyclerView.NO_POSITION
            }
            .setNegativeButton("No", null)
            .show()
    }


    private fun showDeleteAllConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Vaciar carrito")
            .setMessage("¿Estás seguro de que quieres vaciar todo el carrito?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                viewModel.deleteAllProductsFromCart()
                // Deselecciona cualquier elemento
                myAdapter.positionClicked = RecyclerView.NO_POSITION
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvShoppingCart.layoutManager = LinearLayoutManager(requireContext())
            myAdapter = ShoppingCartAdapter(ShoppingCartProduct(ArrayList(),0.0))
            rvShoppingCart.adapter = myAdapter

            btnDelete.setOnClickListener {
                val pos = myAdapter.positionClicked
                if (pos != RecyclerView.NO_POSITION) {
                    showDeleteConfirmationDialog(pos)
                } else {
                    Toast.makeText(requireContext(),
                        "Debes seleccionar un producto primero",
                        Toast.LENGTH_LONG).show()
                }
            }

            btnDeleteAll.setOnClickListener {
                if (viewModel.datos.value?.appCartItems?.isEmpty() == true) {
                    Toast.makeText(requireContext(), "El carrito ya está vacío", Toast.LENGTH_SHORT).show()
                } else {
                    showDeleteAllConfirmationDialog()
//                    showDeleteConfirmationDialog()
                }
            }

            viewModel.datos.observe(viewLifecycleOwner, Observer { shoppingCartData ->
                // Precio total
                txtTotalCartPrice.text = shoppingCartData.totalPrice.toString()
                // Cantidad de líneas
                val totalLineas = shoppingCartData.appCartItems.size
                txtTotalCartQuantity.text = "Productos distintos: $totalLineas"

                // Adapter
                myAdapter = ShoppingCartAdapter(shoppingCartData)
                rvShoppingCart.adapter = myAdapter
            })


            viewModel.deleteFromCartResult.observe(viewLifecycleOwner, Observer { result ->
                // Precio total
                txtTotalCartPrice.text = result.totalPrice.toString()
                // ¡Actualizamos también la cantidad aquí!
                val totalLineas = result.appCartItems.size
                txtTotalCartQuantity.text = "Productos distintos: $totalLineas"

                // Adapter
                myAdapter = ShoppingCartAdapter(result)
                rvShoppingCart.adapter = myAdapter
            })

        }
        viewModel.returnAllCart()
    }
}