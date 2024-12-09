package com.example.aplicacionchofer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionchofer.R

import com.example.aplicacionchofer.models.Orderfre

class FreeOrdersAdapter(private val orders: List<Orderfre>) :
    RecyclerView.Adapter<FreeOrdersAdapter.FreeOrderViewHolder>() {

    class FreeOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOrderId: TextView = itemView.findViewById(R.id.lblidPedidos)
        val txtStatus: TextView = itemView.findViewById(R.id.lblstatusPedidos)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreeOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_free_orders_item, parent, false)
        return FreeOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FreeOrderViewHolder, position: Int) {
        val order = orders[position]
        holder.txtOrderId.text = "Order ID: ${order.id}"
        holder.txtStatus.text = "Status: ${order.status}"

    }

    override fun getItemCount(): Int = orders.size
}