package com.example.proyectofinalricardomitienda

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalricardomitienda.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var myAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding= FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
            myAdapter = ProductAdapter(ResponseProduct())
            rvProducts.adapter = myAdapter
            viewModel.returnAllProducts()
            btnPSearch.setOnClickListener {
                if (txtPSearch.text.isBlank()){
                    if (spinnerPCategory.selectedItemPosition==0){
                        viewModel.returnAllProducts()
                    }else{
                        viewModel.returnAllProductsByCategory(spinnerPCategory.selectedItemPosition.toLong())
                    }
                }else{
                    if (spinnerPCategory.selectedItemPosition==0){
                        viewModel.returnALlProductsByName(txtPSearch.text.toString())
                    }else{
                        viewModel.returnAllProductsByAll(txtPSearch.text.toString(), spinnerPCategory.selectedItemPosition.toLong())
                    }
                }
            }
            viewModel.datos.observe(viewLifecycleOwner, Observer { productData ->
                myAdapter = ProductAdapter(productData)
                rvProducts.adapter = myAdapter
            })

        }

    }
}