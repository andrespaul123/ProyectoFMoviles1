package com.example.proyectofpedidos.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.databinding.ActivityUsuarioFormBinding
import com.example.proyectofpedidos.models.Usuario
import com.example.proyectofpedidos.ui.viewmodels.UsuarioCreateViewmodel

class UsuarioFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsuarioFormBinding
    private val viewModel: UsuarioCreateViewmodel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUsuarioFormBinding.inflate(layoutInflater)
        setContentView(binding.root)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEventListeners()

    }

    fun setupEventListeners() {
        binding.btnGuardarUsuario.setOnClickListener {
            CrearUsuario()
        }
        binding.btnCancelarUsuario.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun CrearUsuario() {
            val usuario = Usuario(
                name = binding.txtUsuarioNombre.text.toString(),
                email = binding.txtUsuarioEmail.text.toString(),
                password = binding.txtUsuarioPassword.text.toString(),
                role = 1
            )
            viewModel.createUsuario(usuario,
                onSuccess = {
                    Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show()
                    //redirigir a la pantalla de login
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                },
                onError = {
                    Toast.makeText(this, "Error al crear usuario", Toast.LENGTH_SHORT).show()
                }
            )


    }

}