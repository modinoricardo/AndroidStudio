package com.example.proyectofinalricardomitienda

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalricardomitienda.databinding.FragmentShoppingCartBinding

class ShoppingCartFragment : Fragment() {

    companion object {
        fun newInstance() = ShoppingCartFragment()
    }

    private val viewModel: ShoppingCartViewModel by viewModels()
    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var myAdapter: ShoppingCartAdapter

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
            myAdapter = ShoppingCartAdapter(ShoppingCartProduct())
            rvShoppingCart.adapter = myAdapter

            btnDelete.setOnClickListener {
                if (myAdapter.positionClicked != RecyclerView.NO_POSITION) {
                    viewModel.deleteProductFromCart(myAdapter.positionClicked)
                    myAdapter.positionClicked = RecyclerView.NO_POSITION
                }else{
                    /*
                                        Toast.makeText(this@ShoppingCartFragment, "Debes seleccionar una fila", Toast.LENGTH_LONG)
                    */
                }
            }

            viewModel.datos.observe(viewLifecycleOwner, Observer { shoppingCartData ->
                txtTotalCartQuantity.text=shoppingCartData.totalCartQuantity.toString()
                txtTotalCartPrice.text=shoppingCartData.totalCartPrice.toString()

                myAdapter = ShoppingCartAdapter(shoppingCartData)
                rvShoppingCart.adapter = myAdapter
            })
            viewModel.deleteFromCartResult.observe(viewLifecycleOwner, Observer { result ->
                txtTotalCartQuantity.text = result.totalCartQuantity.toString()
                txtTotalCartPrice.text = result.totalCartPrice.toString()

                myAdapter = ShoppingCartAdapter(result)
                rvShoppingCart.adapter = myAdapter
            })
        }
        viewModel.returnAllCart()
    }
}