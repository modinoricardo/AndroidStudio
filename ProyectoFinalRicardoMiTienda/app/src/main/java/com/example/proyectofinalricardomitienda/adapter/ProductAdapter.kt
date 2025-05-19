package com.example.proyectofinalricardomitienda.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.activity.ProductDetailActivity
import com.example.proyectofinalricardomitienda.entities.Product
import com.example.proyectofinalricardomitienda.view.ProductView
import com.example.proyectofinalricardomitienda.util.Util
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestListener            // ← aquí
import com.bumptech.glide.request.target.Target             // ← aquí
import com.bumptech.glide.load.engine.GlideException        // ← aquí
import com.bumptech.glide.load.DataSource                  // ← aquí

class ProductAdapter(
    private val items: MutableList<Product>
) : RecyclerView.Adapter<ProductView>() {

    private lateinit var myContexto: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductView {
        myContexto = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_products, parent, false)
        return ProductView(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductView, position: Int) {
        val product = items[position]

        // 1) Construye la URL completa de forma segura:
        val base = Util.URL.removeSuffix("/")        // "http://10.0.2.2:8000/test/app"
        val path = product.imageUrl.removePrefix("/") // "img/products/…"
        val fullUrl = "$base/$path"

        Log.d("ProductAdapter", "Cargando imagen: $fullUrl")

        // 2) Carga la imagen con placeholder, error y fallback:
        Glide.with(holder.productImage.context)
            .load(fullUrl)
            .placeholder(R.drawable.image_carga)
            .error(R.drawable.broken_image)
            .fallback(R.drawable.image_carga)
            .into(holder.productImage)

        // 3) Textos y click:
        holder.txtPName.text        = product.name
        holder.txtPPrice.text       = product.price.toString()
        holder.txtPDescription.text = product.description
        holder.txtPCategory.text    = product.categoryName

        holder.btnAddToCart.setOnClickListener {
            val intent = Intent(myContexto, ProductDetailActivity::class.java).apply {
                putExtra("product_id", product.id)
                putExtra("product_category", product.categoryName)
                putExtra("product_name", product.name)
                putExtra("product_description", product.description)
                putExtra("product_detail", product.productDetail)
                putExtra("product_stock", product.stock)
                putExtra("product_price", product.price)
                putExtra("product_image", fullUrl)
            }
            myContexto.startActivity(intent)
        }
    }

    /**
     * Añade nuevos productos al final y notifica la inserción.
     */
    fun addProducts(newItems: List<Product>) {
        val start = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(start, newItems.size)
    }

    /**
     * Reemplaza por completo la lista de productos (útil en refresh).
     */
    fun replaceAll(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
