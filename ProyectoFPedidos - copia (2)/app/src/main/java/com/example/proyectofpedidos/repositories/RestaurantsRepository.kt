package com.example.proyectofpedidos.repositories

import com.example.practico4m1.api.JSONPlaceHolderService
import com.example.proyectofpedidos.models.CarritoProductos
import com.example.proyectofpedidos.models.Restaurant
import com.example.proyectofpedidos.models.Restaurants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantsRepository {

    fun getRestaurantesList(
        token: String,
        onSuccess: (Restaurants) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.getRestaurantesList("Bearer $token").enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $error")
                }
            }

            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

    fun getRestauranteById(
        token: String,
        id: Int,
        onSuccess: (Restaurant) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.getRestauranteById("Bearer $token", id).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                // Imprime el código de respuesta y el cuerpo completo como cadena
                println("Código de respuesta: ${response.code()}")
                println("Cuerpo completo de la respuesta: ${response.body()}")
                println("ErrorBody: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    response.body()?.let {
                        println("Restaurante obtenido: $it")
                        onSuccess(it)
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $error")
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

}
