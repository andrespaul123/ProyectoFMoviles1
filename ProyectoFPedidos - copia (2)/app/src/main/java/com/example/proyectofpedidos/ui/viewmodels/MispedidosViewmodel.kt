package com.example.proyectofpedidos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofpedidos.models.MisPedidosList
import com.example.proyectofpedidos.repositories.CrearpedidoRepository
import com.example.proyectofpedidos.repositories.RestaurantsRepository

class MispedidosViewmodel : ViewModel() {

    private val _mispedidosList = MutableLiveData<MisPedidosList>().apply {
        value = arrayListOf()
    }
    val pedidosList  = _mispedidosList

    fun getPedidosList(token: String) {
        CrearpedidoRepository.getPedidos(
            token,
            onSuccess = {
                _mispedidosList.value = it
            }, onError = {
                it.printStackTrace()
            }
        )
    }
}






