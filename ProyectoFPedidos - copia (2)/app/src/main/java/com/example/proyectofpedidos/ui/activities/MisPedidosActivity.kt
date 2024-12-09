package com.example.proyectofpedidos.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.databinding.ActivityMisPedidosListBinding
import com.example.proyectofpedidos.ui.adapters.MisPedidosAdapter
import com.example.proyectofpedidos.ui.viewmodels.MispedidosViewmodel

class MisPedidosActivity : AppCompatActivity() {

    private val misPedidosViewModel: MispedidosViewmodel by viewModels()
    private lateinit var binding: ActivityMisPedidosListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_pedidos_list)

        binding = ActivityMisPedidosListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("TOKEN") ?: ""
        if(token!= null){
            misPedidosViewModel.getPedidosList(token)

        }

        setupRecyclerView()
        observeViewModel()


    }

    private fun setupRecyclerView() {
       binding.rvPedidosList.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        misPedidosViewModel.pedidosList.observe(this) { pedidos ->
            binding.rvPedidosList.adapter = MisPedidosAdapter(pedidos)
        }



    }
}
