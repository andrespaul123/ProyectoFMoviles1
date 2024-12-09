package com.example.proyectofpedidos.ui.viewmodels


import androidx.lifecycle.ViewModel
import com.example.proyectofpedidos.models.Usuario
import com.example.proyectofpedidos.repositories.UsuarioRepository

class UsuarioCreateViewmodel : ViewModel() {

    fun createUsuario(usuario: Usuario, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        UsuarioRepository.createUsuario(usuario,
            onSuccess = {
               onSuccess()
            }, onError = {
                onError(it)
            }
        )
    }
}