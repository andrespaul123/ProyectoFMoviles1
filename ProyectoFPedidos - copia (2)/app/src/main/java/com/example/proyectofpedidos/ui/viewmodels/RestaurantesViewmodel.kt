package com.example.proyectofpedidos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofpedidos.models.Restaurants
import com.example.proyectofpedidos.repositories.RestaurantsRepository

class RestaurantesViewmodel : ViewModel() {
    private val _restaurantesList = MutableLiveData<Restaurants>().apply {
        value = arrayListOf()
    }
    val restaurantesList : LiveData<Restaurants> = _restaurantesList

    fun getRestaurantesList(token: String) {
        RestaurantsRepository.getRestaurantesList(
            token,
            onSuccess = {
                _restaurantesList.value = it
            }, onError = {
                it.printStackTrace()
            }
        )
    }


}