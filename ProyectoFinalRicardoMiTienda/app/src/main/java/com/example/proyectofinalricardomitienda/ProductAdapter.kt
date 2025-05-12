package com.example.proyectofinalricardomitienda

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val dataSet: ResponseProduct) : RecyclerView.Adapter<ProductView>() {
    private lateinit var myContexto: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductView {
        myContexto = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_products, parent, false)
        return ProductView(view)
    }

    override fun getItemCount() = dataSet.content!!.size

    override fun onBindViewHolder(holder: ProductView, position: Int) {
        val product = dataSet.content[position]
        val imageUrl = "http://10.0.2.2:8000/test/app" + product.imageUrl  // Construir la URL completa
        Glide.with(myContexto).load(imageUrl).into(holder.productImage)

        holder.txtPName.text = product.name
        holder.txtPPrice.text = product.price.toString()
        holder.txtPDescription.text = product.description
        holder.txtPCategory.text = product.categoryName

        Log.d("IMG_URL", imageUrl ?: "url vacía")  // Mostrar la URL completa en los logs

        holder.btnAddToCart.setOnClickListener {
            val intent = Intent(myContexto, ProductDetailActivity::class.java).apply {
                putExtra("product_id", product.id)
                putExtra("product_name", product.name)
                putExtra("product_description", product.description)
                putExtra("product_image", product.imageUrl)
                putExtra("product_price", product.price)
                putExtra("product_category", product.categoryName)
            }
            myContexto.startActivity(intent)
        }
    }

}