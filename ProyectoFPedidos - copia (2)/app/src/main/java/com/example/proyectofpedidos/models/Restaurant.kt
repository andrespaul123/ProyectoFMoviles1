package com.example.proyectofpedidos.models

data class Restaurant  (
    val id: Int,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val logo: String,
    val products: ArrayList<Producto> = arrayListOf(),

)

typealias Restaurants = ArrayList<Restaurant>
