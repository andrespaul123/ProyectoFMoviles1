package com.example.proyectofpedidos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofpedidos.models.Productos
import com.example.proyectofpedidos.repositories.RestaurantsRepository

class RestauranteDetalleViewmodel : ViewModel() {
    private val _products = MutableLiveData<Productos>().apply {
        value = arrayListOf()
    }
    val products: LiveData<Productos> = _products


    fun getRestauranteById(id: Int,token: String) {
        RestaurantsRepository.getRestauranteById(
            token,id,
            onSuccess = {
                _products.value = it.products
            }, onError = {
                it.printStackTrace()
            }
        )
    }

}