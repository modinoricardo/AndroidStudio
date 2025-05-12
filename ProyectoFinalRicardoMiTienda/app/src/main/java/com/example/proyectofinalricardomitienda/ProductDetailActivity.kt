package com.example.proyectofinalricardomitienda

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.proyectofinalricardomitienda.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ShoppingCartViewModel by viewModels()
    private var productId: Long = 0
    private var productName: String = ""
    private var productDescription: String = ""
    private var productImage: String = ""
    private var productPrice: Double = 0.0
    private var productCategory: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        productId = intent.getLongExtra("product_id", 0)
        productName = intent.getStringExtra("product_name") ?: ""
        productDescription = intent.getStringExtra("product_description") ?: ""
        productImage = intent.getStringExtra("product_image") ?: ""
        productPrice = intent.getDoubleExtra("product_price", 0.0)
        productCategory = intent.getIntExtra("product_category", 0)

        with(binding) {
            txtDetailProductName.text = productName
            txtDetailProductDescription.text = productDescription
            txtDetailProductPrice.text = productPrice.toString()

            Glide.with(this@ProductDetailActivity)
                .load(productImage)
                .into(imgDetailProduct)

            when (productCategory) {
                1 -> txtDetailProductCategory.text = "Carta"
                2 -> txtDetailProductCategory.text = "Sobre"
                3 -> txtDetailProductCategory.text = "Caja"
                else -> txtDetailProductCategory.text = "Otro"
            }

            txtQuantity.text = "1"

            btnIncrement.setOnClickListener {
                val currentQuantity = txtQuantity.text.toString().toInt()
                txtQuantity.text = (currentQuantity + 1).toString()
            }

            btnDecrement.setOnClickListener {
                val currentQuantity = txtQuantity.text.toString().toInt()
                if (currentQuantity > 1) {
                    txtQuantity.text = (currentQuantity - 1).toString()
                }
            }

            btnAddToCart.setOnClickListener {
                val quantity = txtQuantity.text.toString().toInt()
                viewModel.addProductToCart(productId, quantity)
            }

            btnBack.setOnClickListener {
                finish()
            }
        }

        viewModel.addToCartResult.observe(this) { result ->
            if (result != null) {
                Toast.makeText(this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Stock insuficiente", Toast.LENGTH_SHORT).show()
            }
        }
    }

}