package com.example.proyectofpedidos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.models.MisPedidos


class MisPedidosAdapter(private val orders: List<MisPedidos>) :
    RecyclerView.Adapter<MisPedidosAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtidMe: TextView = itemView.findViewById(R.id.lblidMe)
        val txtdriverMe: TextView = itemView.findViewById(R.id.lbldriverMe)
        val txtstatusMe: TextView = itemView.findViewById(R.id.lblstatusMe)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_mis_pedidos, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.txtidMe.text = order.id.toString()
        holder.txtdriverMe.text = order.driver_id.toString()
        holder.txtstatusMe.text = order.status

    }

    override fun getItemCount(): Int {
        return orders.size
    }
}