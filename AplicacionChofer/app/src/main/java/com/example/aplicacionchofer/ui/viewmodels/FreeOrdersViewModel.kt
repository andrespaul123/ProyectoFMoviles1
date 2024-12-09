package com.example.aplicacionchofer.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplicacionchofer.models.MisPedidosList
import com.example.aplicacionchofer.repositories.PedidosRepository

class FreeOrdersViewModel : ViewModel() {

    private val _freeOrdersList = MutableLiveData<MisPedidosList>().apply {
        value = arrayListOf()
    }
    val pedidosList = _freeOrdersList

    fun fetchFreeOrders(token: String) {
        PedidosRepository.getPedidos(
            token,
            onSuccess = {
                _freeOrdersList.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}