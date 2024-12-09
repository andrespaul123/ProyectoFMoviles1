package com.example.aplicacionchofer.repositories

import com.example.aplicacionchofer.api.JSONPlaceHolderService
import com.example.aplicacionchofer.models.Chofer
import com.example.aplicacionchofer.models.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ChoferRepository {

    fun createChofer(
        usuario: Chofer,
        onSuccess: (Chofer) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.createChofer(usuario).enqueue(object : Callback<Chofer> {
            override fun onResponse(call: Call<Chofer>, response: Response<Chofer>) {
                if (response.isSuccessful) {
                    val createdUsuario = response.body()
                    onSuccess(createdUsuario!!)
                } else {
                    println("Error en la respuesta: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Chofer>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }



    fun loginUsuario(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        val credentials = mapOf("email" to email, "password" to password)

        service.loginUsuario(credentials).enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    val token = response.body()
                    onSuccess(token!!.access_token)
                } else {
                    val error = response.errorBody()?.string()?: "Error desconocido"
                    println("Error en la respuesta: $error")

                }
            }
            override fun onFailure(call: Call<Token>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })

    }

    fun getUsuarioProfile(
        token: String,
        onSuccess: (Chofer) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.getPerfil("Bearer $token").enqueue(object : Callback<Chofer> {
            override fun onResponse(call: Call<Chofer>, response: Response<Chofer>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    onSuccess(usuario!!)
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $error")
                    onError(Exception("Error: $error"))
                }
            }

            override fun onFailure(call: Call<Chofer>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }


}