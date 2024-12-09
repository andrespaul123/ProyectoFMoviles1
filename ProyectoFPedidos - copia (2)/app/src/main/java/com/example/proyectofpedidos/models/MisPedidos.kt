package com.example.proyectofpedidos.models

data class MisPedidos(
        val  id: Int,
        val user_id: Int,
        val restaurant_id: Int,
        val total: Double,
        val latitude: String,
        val longitude: String,
        val address: String,
        val driver_id: Any? = null,
        val status: String,
        val created_at: String,
        val delivery_proof: String,
        val order_details: List<CarritoProductomodel>
)
typealias MisPedidosList = ArrayList<MisPedidos>



