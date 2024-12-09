package com.example.aplicacionchofer.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.aplicacionchofer.models.Chofer
import com.example.aplicacionchofer.repositories.ChoferRepository

class ChoferCreateViewmodel : ViewModel() {

    fun createChofer(chofer: Chofer, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        ChoferRepository.createChofer(chofer,
            onSuccess = {
               onSuccess()
            }, onError = {
                onError(it)
            }
        )
    }



}