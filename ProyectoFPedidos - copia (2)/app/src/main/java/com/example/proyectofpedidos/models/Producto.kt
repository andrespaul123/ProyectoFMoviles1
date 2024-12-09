package com.example.proyectofpedidos.models

data class Producto (
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val restaurant_id: Int,
    val image: String,

)
typealias Productos = ArrayList<Producto>
