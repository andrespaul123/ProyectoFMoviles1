package com.example.proyectofpedidos.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofpedidos.R
import com.example.proyectofpedidos.models.Restaurant
import com.example.proyectofpedidos.models.Restaurants

class RestaurantesAdapter (private val listener: RestaurantItemListener
) : RecyclerView.Adapter<RestaurantesAdapter.RestaurantViewHolder>() {

    private var restaurantList: Restaurants = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurants_item_comp, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantList[position], listener)
    }

    override fun getItemCount(): Int = restaurantList.size

    fun updateData(newData: Restaurants) {
        restaurantList = newData
        notifyDataSetChanged()
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lblRestaurantName: TextView = itemView.findViewById(R.id.lblRestaurantName)
        private val lblRestaurantAddress: TextView = itemView.findViewById(R.id.lblRestaurantAddress)
        private val lblImagenRestaurante: ImageView = itemView.findViewById(R.id.lblImagenRestaurante)

        fun bind(restaurant: Restaurant, listener: RestaurantItemListener) {
            lblRestaurantName.text = restaurant.name
            lblRestaurantAddress.text = restaurant.address
            // Carga la imagen con Glide
            Glide.with(itemView.context)
                .load(restaurant.logo)
                .into(lblImagenRestaurante)
            itemView.setOnClickListener { listener.onRestaurantClick(restaurant) }
        }
    }

    interface RestaurantItemListener {
        fun onRestaurantClick(restaurant: Restaurant)
    }
}