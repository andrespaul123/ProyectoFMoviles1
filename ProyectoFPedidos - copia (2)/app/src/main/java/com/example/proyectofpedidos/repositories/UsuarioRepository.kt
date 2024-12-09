package com.example.proyectofpedidos.repositories

import com.example.practico4m1.api.JSONPlaceHolderService
import com.example.proyectofpedidos.models.Token
import com.example.proyectofpedidos.models.Usuario
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


object UsuarioRepository {

    fun createUsuario(
        usuario: Usuario,
        onSuccess: (Usuario) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.createUsuario(usuario).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val createdUsuario = response.body()
                    onSuccess(createdUsuario!!)
                } else {
                    println("Error en la respuesta: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
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
        onSuccess: (Usuario) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.getUsuarioProfile("Bearer $token").enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    onSuccess(usuario!!)
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $error")
                    onError(Exception("Error: $error"))
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }
}