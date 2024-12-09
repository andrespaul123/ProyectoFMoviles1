package com.example.proyectofpedidos.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofpedidos.databinding.ActivityMePerfilBinding
import com.example.proyectofpedidos.ui.viewmodels.MeViewmodel

class MeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMePerfilBinding
    private val viewModel: MeViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMePerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = intent.getStringExtra("TOKEN") ?: ""
        viewModel.getUsuarioProfile(token)

        setupEventListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.usuario.observe(this) { usuario ->
            binding.lblUsuarioName.text = usuario.name
            binding.lblUsuarioEmail.text = usuario.email
            binding.lblUsuarioRole.text = if (usuario.profile?.role == 1) "Usuario: Cliente" else "Usuario: ${usuario.profile?.role ?: "No definido"}"

        }
    }
    private fun setupEventListeners() {
        binding.btnmisPedidos.setOnClickListener {
            val intent = Intent(this, MisPedidosActivity::class.java).apply {
                putExtra("TOKEN", intent.getStringExtra("TOKEN"))
            }
            startActivity(intent)
        }
    }
}