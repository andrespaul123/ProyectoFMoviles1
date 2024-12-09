package com.example.proyectofpedidos.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.proyectofpedidos.models.Pedido
import com.example.proyectofpedidos.models.Usuario
import com.example.proyectofpedidos.repositories.CrearpedidoRepository
import com.example.proyectofpedidos.repositories.UsuarioRepository

class PedidoViewmodel: ViewModel() {

    fun crearPedido(token: String,pedido: Pedido, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        CrearpedidoRepository.createPedido(
            token=token,
                    pedido = pedido,
            onSuccess = {
                onSuccess()
            }, onError = {
                onError(it)
            }
        )

    }
}
