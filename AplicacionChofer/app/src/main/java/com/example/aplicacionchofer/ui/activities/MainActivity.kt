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
import com.example.aplicacionchofer.databinding.ActivityMainBinding
import com.example.aplicacionchofer.repositories.ChoferRepository
import com.example.aplicacionchofer.ui.viewmodels.ChoferCreateViewmodel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ChoferCreateViewmodel by viewModels()

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
        binding.btnRegistrarLogin.setOnClickListener {
            val intent = Intent(this, ChoferFormActivity::class.java)
            startActivity(intent)
        }

    }

    fun IniciarSession() {
        val email = binding.txtEmailLogin.editText?.text.toString()
        val password = binding.txtPasswordLogin.editText?.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        ChoferRepository.loginUsuario(
            email, password,
            onSuccess = { token ->
                // Validar el perfil del usuario después de iniciar sesión
                ChoferRepository.getUsuarioProfile(
                    token,
                    onSuccess = { usuario ->
                        println("Usuario: $usuario")
                        if (usuario.profile?.role == 2) {// Cambia según el rol que deseas validar
                            // Guardar token y redirigir si el rol es válido
                            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            sharedPreferences.edit().putString("token_acceso", token).apply()

                            Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, FreeOrdersActivity::class.java)
                            intent.putExtra("TOKEN", token)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "No tienes permisos para iniciar sesión", Toast.LENGTH_SHORT).show()
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