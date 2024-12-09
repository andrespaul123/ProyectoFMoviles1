package com.example.aplicacionchofer.repositories

import com.example.aplicacionchofer.api.JSONPlaceHolderService
import com.example.aplicacionchofer.models.MisPedidosList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PedidosRepository {

    fun getPedidos(
        token: String,
        onSuccess: (MisPedidosList) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        println("Token: $token")

        service.getPedidos("Bearer $token").enqueue(object : Callback<MisPedidosList> {
            override fun onResponse(call: Call<MisPedidosList>, response: Response<MisPedidosList>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                        println("Pedidos obtenidos con éxito: $it")
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $error")
                }
            }

            override fun onFailure(call: Call<MisPedidosList>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }
}