package com.example.proyectofpedidos.models

data class Usuario (
    //val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: Int,
    val profile: Profile? = null // Asegúrate de incluir el perfil si es necesario
)
typealias Usuarios = ArrayList<Usuario>