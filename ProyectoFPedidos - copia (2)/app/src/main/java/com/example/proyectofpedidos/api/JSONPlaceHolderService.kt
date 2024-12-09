package com.example.practico4m1.api



import com.example.proyectofpedidos.models.CarritoProductos
import com.example.proyectofpedidos.models.MisPedidosList
import com.example.proyectofpedidos.models.Pedido
import com.example.proyectofpedidos.models.Restaurant
import com.example.proyectofpedidos.models.Restaurants
import com.example.proyectofpedidos.models.Token
import com.example.proyectofpedidos.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface JSONPlaceHolderService {

    //Usuario
    @POST("api/users")
    fun createUsuario(@Body usuario: Usuario): Call<Usuario>

    @POST("api/users/login")
    fun loginUsuario(@Body credentials: Map<String, String>): Call<Token>

    @GET("api/restaurants")
    fun getRestaurantesList(@Header("Authorization") token: String): Call<Restaurants>

    @GET("api/restaurants/{id}")
    fun getRestauranteById(@Header("Authorization") token: String, @Path("id")id: Int): Call<Restaurant>

    @GET("api/me")
    fun getUsuarioProfile(@Header("Authorization") token: String): Call<Usuario>

    //CREAR PEDIDO
    @POST("api/orders")
    fun crearPedido(@Header("Authorization") token: String, @Body pedido: Pedido): Call<Pedido>

    //Mostrar detalle de pedido
    @GET("api/orders")
    fun getPedidos(@Header("Authorization") token: String): Call<MisPedidosList>


}