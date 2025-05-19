package com.example.proyectofinalricardomitienda.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.databinding.ActivityProductDetailBinding
import com.example.proyectofinalricardomitienda.model.ShoppingCartViewModel

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ShoppingCartViewModel by viewModels()

    private var productId: Long = 0
    private var productCategoryName: String = ""
    private var productName: String = ""
    private var productDescription: String = ""
    private var productDetail: String = ""
    private var productStock: Int = 0
    private var productPrice: Double = 0.0
    private var productImageUrl: String = ""

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
        productCategoryName = intent.getStringExtra("product_category") ?: ""
        productName = intent.getStringExtra("product_name") ?: ""
        productDescription = intent.getStringExtra("product_description") ?: ""
        productDetail = intent.getStringExtra("product_detail") ?: ""
        productStock = intent.getIntExtra("product_stock", 0)
        productPrice = intent.getDoubleExtra("product_price", 0.0)
        productImageUrl = intent.getStringExtra("product_image") ?: ""

        with(binding) {
            txtDetailProductName.text = productName
            txtDetailProductCategory.text = productCategoryName
            txtDetailProductDescription.text = productDescription
            txtDetailProductPrice.text = productPrice.toString()

            Glide.with(this@ProductDetailActivity).load(productImageUrl).into(imgDetailProduct)

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

        viewModel.cartError.observe(this) { result ->
            //Si el mensaje de error es != null mostrar carrito
            if (result == null) {
                Toast.makeText(this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            }
        }
    }

}