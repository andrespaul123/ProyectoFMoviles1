package com.example.proyectofpedidos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.models.Producto
import com.example.proyectofpedidos.models.Productos

class ProductosAdapter (private val onagregarproductos: (Producto) -> Unit): RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    private var listaProducto: Productos = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_item_detalle, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(listaProducto[position], onagregarproductos)
    }

    override fun getItemCount(): Int {
        return listaProducto.size
    }

    fun updateData(newData: Productos) {
        listaProducto = newData
        notifyDataSetChanged()
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lblProductName: TextView = itemView.findViewById(R.id.lblProductName)
        private val lblProductDescription: TextView = itemView.findViewById(R.id.lblProductDescription)
        private val lblProductPrice: TextView = itemView.findViewById(R.id.lblProductPrice)
        private val lblProductAdd: Button = itemView.findViewById(R.id.btnAgregarProducto)
        private val lblProductImage: ImageView = itemView.findViewById(R.id.lblProductImage)

        fun bind(producto: Producto , onagregarproductos: (Producto) -> Unit) {
            lblProductName.text = producto.name
            lblProductDescription.text = producto.description
            lblProductPrice.text = producto.price
            Glide.with(itemView.context)
                .load(producto.image)
                .into(lblProductImage)
            lblProductAdd.setOnClickListener {
                onagregarproductos(producto)

            }
        }
    }
}