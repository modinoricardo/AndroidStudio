package com.example.proyectofinalricardomitienda.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.activity.ProductDetailActivity
import com.example.proyectofinalricardomitienda.entities.Product
import com.example.proyectofinalricardomitienda.view.ProductView
import com.example.proyectofinalricardomitienda.util.Util

class ProductAdapter() : RecyclerView.Adapter<ProductView>() {

    private lateinit var myContexto: Context
    private val miListaProductos = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductView {
        myContexto = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_products, parent, false)
        return ProductView(view)
    }

    override fun getItemCount(): Int = miListaProductos.size

    override fun onBindViewHolder(holder: ProductView, position: Int) {
        val product = miListaProductos[position]

        // 1) Construye la URL completa de forma segura:
//        val base = Util.URL.removeSuffix("/")        // "http://10.0.2.2:8000/test/app"
        val base = Util.URL2.removeSuffix("/")
        val path = product.imageUrl.removePrefix("/") // "img/products/…"
        val fullUrl = "$base/$path"
//        val fullUrl = "http://192.168.1.135:8000/test/app/img/products/coffees/cappuccino.jpg"

        Log.d("ProductAdapter", "Cargando imagen: $fullUrl")

        val options = RequestOptions()
            .disallowHardwareConfig()
        // 2) Carga la imagen con placeholder, error y fallback:
        Log.i("image",fullUrl)
        //No funciona con la url 10.0.2.2 asi que necesito usar la ip del ordenador,
        //si estuviera desplegado en azure el problema se solucionaria por poner la ip del servidor desplegado
        Glide.with(myContexto)
            .load(fullUrl)
            .apply(options)
//            .placeholder(R.drawable.image_carga)
//            .error(R.drawable.broken_image)
//            .fallback(R.drawable.image_carga)
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

    fun updateListProduct(newItems: List<Product>) {
        val startPosition = miListaProductos.size
        miListaProductos.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

}
