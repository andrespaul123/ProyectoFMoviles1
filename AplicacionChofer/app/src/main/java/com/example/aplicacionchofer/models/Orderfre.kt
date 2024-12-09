package com.example.aplicacionchofer.models

data class Orderfre (
    val id: Int,
    val user_id: Int,
    val restaurant_id: Int,
    val total: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val driver_id: Any?,
    val status: String,
    val created_at: String,
    val delivery_proof: String,
    //val order_details: List<OrderDetail>
)

typealias MisPedidosList = ArrayList<Orderfre>