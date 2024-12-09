package com.example.aplicacionchofer.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionchofer.R
import com.example.aplicacionchofer.databinding.ActivityFreeOrdersListBinding
import com.example.aplicacionchofer.ui.adapters.FreeOrdersAdapter
import com.example.aplicacionchofer.ui.viewmodels.FreeOrdersViewModel

class FreeOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFreeOrdersListBinding
    private val freeOrdersViewModel: FreeOrdersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeOrdersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("TOKEN") ?: ""
        if (token.isNotEmpty()) {
            freeOrdersViewModel.fetchFreeOrders(token)
        } else {
            Toast.makeText(this, "Token no proporcionado", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvFreeOrdersList.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        freeOrdersViewModel.pedidosList.observe(this) { orders ->
            binding.rvFreeOrdersList.adapter = FreeOrdersAdapter(orders)
        }
    }
}