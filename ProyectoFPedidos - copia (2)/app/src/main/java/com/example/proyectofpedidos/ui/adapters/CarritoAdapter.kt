package com.example.proyectofpedidos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.models.CarritoProductomodel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarritoAdapter(
    private val carritoCompras: ArrayList<CarritoProductomodel>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {


    class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lblProductName: TextView = itemView.findViewById(R.id.lblcarritonombreP)
        private val lblProductPrice: TextView = itemView.findViewById(R.id.lblcarritoPrecioP)
        private val lblProductQty: TextView = itemView.findViewById(R.id.lblcarritocantidadP)
        val btnMas: FloatingActionButton = itemView.findViewById(R.id.btnMas)
        val btnMenos: FloatingActionButton = itemView.findViewById(R.id.btnMenos)


        fun bind(producto: CarritoProductomodel) {
            lblProductName.text = producto.nombre
            lblProductPrice.text = producto.price
            lblProductQty.text = producto.qty.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carrito_item_productos, parent, false)
        return CarritoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return carritoCompras.size


    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        holder.bind(carritoCompras[position])

        holder.btnMas.setOnClickListener {
            carritoCompras[position].qty++
            notifyItemChanged(position)
            onQuantityChanged()

        }
        holder.btnMenos.setOnClickListener {
            if (carritoCompras[position].qty > 1) {
                carritoCompras[position].qty--
                notifyItemChanged(position)
                onQuantityChanged()
            }


        }

    }
}
