package com.example.proyectofpedidos.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.databinding.ActivityRestauranteDetalleListBinding
import com.example.proyectofpedidos.models.CarritoProductomodel
import com.example.proyectofpedidos.models.Producto
import com.example.proyectofpedidos.ui.adapters.ProductosAdapter
import com.example.proyectofpedidos.ui.viewmodels.RestauranteDetalleViewmodel

class RestauranteDetalleList : AppCompatActivity() {
    private val ListcarritoProductos : ArrayList<CarritoProductomodel> = arrayListOf()
    private lateinit var binding: ActivityRestauranteDetalleListBinding
    private var restaurante_id :Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRestauranteDetalleListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListaProductos()

        val restaurantId = intent.getIntExtra("RESTAURANT_ID", -1)
        restaurante_id = restaurantId
        val token = intent.getStringExtra("TOKEN") ?: ""
        println("Token de restauranteDetalleList: $token")

        if (restaurantId != -1) {
            setupRecyclerView()
            fetchProducts(restaurantId, token)
        } else {

        }
    }

    private fun setupListaProductos() {
        binding.faListaPedidos.setOnClickListener {
            val intent = Intent(this, CarritoProducto::class.java).apply {
                putExtra("CARRITO_PRODUCTOS", ListcarritoProductos)
                putExtra("RESTAURANTE_ID", restaurante_id)
                putExtra("TOKEN", intent.getStringExtra("TOKEN"))
            }
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        val adapter = ProductosAdapter{ producto ->
            agregarProducto(producto)
        }
        findViewById<RecyclerView>(R.id.rvDetalleList).apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@RestauranteDetalleList)

        }
    }

    private fun agregarProducto(producto: Producto) {
        val existingProduct = ListcarritoProductos.find { it.product_id == producto.id }

        if (existingProduct != null) {
            existingProduct.qty += 1
        } else {
            val newItem = CarritoProductomodel(
                product_id = producto.id,
                nombre = producto.name,
                qty = 1,
                price = producto.price
            )
            ListcarritoProductos.add(newItem)
        }

        println("Lista actualizada de productos: $ListcarritoProductos")
    }


    private fun fetchProducts(restaurantId: Int, token: String) {
        val viewModel: RestauranteDetalleViewmodel by viewModels()
        viewModel.getRestauranteById(restaurantId, token)
        viewModel.products.observe(this) { products ->
            val adapter = findViewById<RecyclerView>(R.id.rvDetalleList).adapter as ProductosAdapter
            adapter.updateData(products)
        }
    }
}
