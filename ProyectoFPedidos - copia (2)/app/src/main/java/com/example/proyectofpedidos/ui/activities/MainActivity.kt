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
import com.example.proyectofpedidos.databinding.ActivityMainBinding
import com.example.proyectofpedidos.repositories.UsuarioRepository
import com.example.proyectofpedidos.ui.viewmodels.UsuarioCreateViewmodel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UsuarioCreateViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnIniciarSesion.setOnClickListener {

            IniciarSession()

        }
        binding.btnRegistrarLogin.setOnClickListener{
            val intent = Intent(this, UsuarioFormActivity::class.java)
            startActivity(intent)
        }

    }

    fun IniciarSession() {
        val email = binding.txtEmailLogin.editText?.text.toString()
        val password = binding.txtPasswordLogin.editText?.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        UsuarioRepository.loginUsuario(
            email, password,
            onSuccess = { token ->
                // Validar el perfil del usuario después de iniciar sesión
                UsuarioRepository.getUsuarioProfile(
                    token,
                    onSuccess = { usuario ->
                     println("Usuario: $usuario")
                        if (usuario.profile?.role == 1) {// Cambia según el rol que deseas validar
                            // Guardar token y redirigir si el rol es válido
                            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            sharedPreferences.edit().putString("token_acceso", token).apply()

                            Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Restaurantes::class.java)
                            intent.putExtra("TOKEN", token)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "No eres un cliente válido para iniciar sesión", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onError = {
                        Toast.makeText(this, "Error al obtener el perfil del usuario", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onError = {
                Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        )
    }
}