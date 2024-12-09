package com.example.aplicacionchofer.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacionchofer.R
import com.example.aplicacionchofer.databinding.ActivityChoferFormregisterBinding
import com.example.aplicacionchofer.models.Chofer
import com.example.aplicacionchofer.ui.viewmodels.ChoferCreateViewmodel

class ChoferFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChoferFormregisterBinding
    private val viewModel: ChoferCreateViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityChoferFormregisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()

    }
    private fun setupEventListeners() {
        binding.btnRegistrarChofer.setOnClickListener {
            crearChofer()
        }
        binding.btnCancelarChofer.setOnClickListener {
            val  intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

     fun crearChofer() {

        val chofer = Chofer(
            name = binding.txtChoferNombre.editText?.text.toString(),
            email = binding.txtChoferEmail.editText?.text.toString(),
            password = binding.txtChoferPassword.editText?.text.toString(),
            role = 2
        )
        println("Datos del chofer: $chofer")
        viewModel.createChofer(chofer,
            onSuccess = {
                Toast.makeText(this, "Chofer creado ", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            onError = {
                Toast.makeText(this, "Error al crear chofer", Toast.LENGTH_SHORT).show()
            }
        )
    }

}