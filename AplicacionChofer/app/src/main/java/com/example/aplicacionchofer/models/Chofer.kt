package com.example.aplicacionchofer.models

data class Chofer (

    val name : String,
    val email : String,
    val password : String,
    val role : Int,
    val profile: Profile? = null

)
typealias Chofers = ArrayList<Chofer>

