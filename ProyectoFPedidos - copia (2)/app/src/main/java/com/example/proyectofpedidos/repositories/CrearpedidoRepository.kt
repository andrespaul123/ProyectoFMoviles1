package com.example.proyectofpedidos.repositories
import com.example.practico4m1.api.JSONPlaceHolderService
import com.example.proyectofpedidos.models.MisPedidosList
import com.example.proyectofpedidos.models.Pedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
object CrearpedidoRepository {

    fun createPedido(
        token: String,
        pedido: Pedido,
        onSuccess: (Pedido) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.crearPedido("Bearer $token", pedido).enqueue(object : Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        println("Pedido creado con éxito: $it")
                        onSuccess(it)
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $error")
                    onError(Throwable(error))
                }
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

    fun getPedidos(
        token: String,
        onSuccess: (MisPedidosList) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        println("Token: $token")

        service.getPedidos("Bearer $token").enqueue(object : Callback<MisPedidosList> {
            override fun onResponse(call: Call <MisPedidosList>, response: Response<MisPedidosList>) {
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
