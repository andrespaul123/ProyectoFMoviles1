package com.example.proyectofpedidos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofpedidos.models.Usuario
import com.example.proyectofpedidos.repositories.UsuarioRepository

class MeViewmodel : ViewModel() {
    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    fun getUsuarioProfile(token: String) {
        UsuarioRepository.getUsuarioProfile(
            token,
            onSuccess = {
                _usuario.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}