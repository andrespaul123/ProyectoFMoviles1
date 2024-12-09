package com.example.proyectofpedidos.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarritoProductomodel (
    val  product_id: Int,
    val nombre: String = "",
    var qty: Int ,
    val price: String,


): Parcelable
typealias CarritoProductos = ArrayList<CarritoProductomodel>