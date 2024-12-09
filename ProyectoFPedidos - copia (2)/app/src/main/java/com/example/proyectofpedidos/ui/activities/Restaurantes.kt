package com.example.proyectofpedidos.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofpedidos.databinding.ActivityRestaurantesBinding
import com.example.proyectofpedidos.models.Restaurant
import com.example.proyectofpedidos.ui.adapters.RestaurantesAdapter
import com.example.proyectofpedidos.ui.viewmodels.RestaurantesViewmodel

class Restaurantes : AppCompatActivity(), RestaurantesAdapter.RestaurantItemListener {

    private lateinit var binding: ActivityRestaurantesBinding
    private val viewModel: RestaurantesViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()

        val token = intent.getStringExtra("TOKEN") ?: ""
        viewModel.getRestaurantesList(token)

        // Redirigir a la actividad MeActivity cuando se hace clic en el botón btnMeperfil
        binding.btnMeperfil.setOnClickListener {
            val intent = Intent(this, MeActivity::class.java).apply {
                putExtra("TOKEN", token)  // Pasa el token a la actividad de perfil
            }
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        val adapter = RestaurantesAdapter(this)
        binding.rvRestauranteList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@Restaurantes)
        }
    }

    private fun setupObservers() {
        viewModel.restaurantesList.observe(this) { restaurants ->
            val adapter = binding.rvRestauranteList.adapter as RestaurantesAdapter
            adapter.updateData(restaurants)
        }
    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        val intent = Intent(this, RestauranteDetalleList::class.java).apply {
            putExtra("RESTAURANT_ID", restaurant.id)
            putExtra("TOKEN", intent.getStringExtra("TOKEN"))
        }
        startActivity(intent)
    }
}