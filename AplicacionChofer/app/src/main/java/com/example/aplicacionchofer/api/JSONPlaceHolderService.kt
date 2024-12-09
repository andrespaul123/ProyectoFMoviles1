package com.example.aplicacionchofer.api

import com.example.aplicacionchofer.models.Chofer
import com.example.aplicacionchofer.models.MisPedidosList
import com.example.aplicacionchofer.models.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface JSONPlaceHolderService {


    @POST("api/users")
    fun createChofer(@Body chofer: Chofer): Call<Chofer>

    @POST("api/users/login")
    fun loginUsuario(@Body credentials: Map<String, String>): Call<Token>

    @GET("api/me")
    fun getPerfil(@Header("Authorization") token: String): Call<Chofer>


    @GET("api/orders/free")
    fun getPedidos(@Header("Authorization") token: String): Call<MisPedidosList>

}