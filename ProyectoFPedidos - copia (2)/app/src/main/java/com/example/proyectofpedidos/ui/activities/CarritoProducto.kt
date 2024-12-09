package com.example.proyectofpedidos.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.databinding.ActivityCarritoProductoBinding
import com.example.proyectofpedidos.models.CarritoProductomodel
import com.example.proyectofpedidos.ui.adapters.CarritoAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.proyectofpedidos.models.Pedido
import com.example.proyectofpedidos.repositories.CrearpedidoRepository
import com.example.proyectofpedidos.ui.viewmodels.PedidoViewmodel
import com.example.proyectofpedidos.ui.viewmodels.UsuarioCreateViewmodel

class CarritoProducto : AppCompatActivity() {
    private lateinit var carritoCompras: ArrayList<CarritoProductomodel>
    private var restaurante_id: Int = -1
    private var token: String? = null
    private val viewModel: PedidoViewmodel by viewModels()

    private lateinit var binding: ActivityCarritoProductoBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarritoProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        token = intent.getStringExtra("TOKEN") ?: ""
        println("Token de Carrito De Productost: $token")

        carritoCompras = intent.getParcelableArrayListExtra("CARRITO_PRODUCTOS") ?: arrayListOf()
        restaurante_id = intent.getIntExtra("RESTAURANTE_ID", -1)
        println("Restaurante ID recibido: $restaurante_id")

        setupRecyclerView()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnUbicacion.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, 1)
        }
        binding.btnUbicacionactual.setOnClickListener {
            obtenerUbicacionActual()
        }
        binding.btnCrearPedido.setOnClickListener {
            crearPedido()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val latitud = data?.getDoubleExtra("LATITUD", 0.0)
            val longitud = data?.getDoubleExtra("LONGITUD", 0.0)
            binding.lblLatitud.text = latitud.toString()
            binding.lblLongitud.text = longitud.toString()
        }
    }

    private fun calcularTotal(): Double {
        var total = 0.0
        carritoCompras.forEach { producto ->
            total += producto.qty * producto.price.toDouble()
        }
        return total
    }

    private fun crearPedido() {
        val detalle = carritoCompras.map {
            CarritoProductomodel(
                product_id = it.product_id,
                qty = it.qty,
                price = it.price.toString()
            )
        }
        val pedido = Pedido(
            restaurant_id = restaurante_id,
            total = calcularTotal(),
            address = binding.lblDireccionn.text.toString(),
            longitude = binding.lblLongitud.text.toString(),
            latitude = binding.lblLatitud.text.toString(),
            details = detalle
        )
        println("Pedido: $pedido")
        viewModel.crearPedido(token!!, pedido,
            onSuccess = {
                Toast.makeText(this, "Pedido creado", Toast.LENGTH_SHORT).show()
                finish()
            },
            onError = {
                Toast.makeText(this, "Error al crear pedido", Toast.LENGTH_SHORT).show()
            }
        )
    }



    private fun obtenerUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            solicitarPermisos.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitud = location.latitude
                val longitud = location.longitude

                binding.lblLatitud.text = latitud.toString()
                binding.lblLongitud.text = longitud.toString()
            } else {
                Toast.makeText(this, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al obtener la ubicación: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private val solicitarPermisos = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            obtenerUbicacionActual()
        } else {
            Toast.makeText(this, "Permisos denegados.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        val adapter = CarritoAdapter(carritoCompras) {
            mostrarTotal()
        }
        findViewById<RecyclerView>(R.id.rvCarritoList).apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@CarritoProducto)
        }
        mostrarTotal()
    }

    private fun mostrarTotal() {
        val total = calcularTotal()
        binding.lblTotal.text = String.format("Total: $ %.2f", total)
    }
}