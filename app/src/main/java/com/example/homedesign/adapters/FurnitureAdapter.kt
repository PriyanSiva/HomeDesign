package com.example.homedesign.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homedesign.R
import com.example.homedesign.activities.FurnitureActivity
import com.example.homedesign.models.Furniture

class FurnitureAdapter(private val furnitureList: List<Furniture>) :
    RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>() {

    class FurnitureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val furnitureImage: ImageView = itemView.findViewById(R.id.furniture_image)
        val furnitureName: TextView = itemView.findViewById(R.id.furniture_name)
        val furniturePrice: TextView = itemView.findViewById(R.id.furniture_price)
        val furnitureDetails: TextView = itemView.findViewById(R.id.furniture_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_furniture, parent, false)
        return FurnitureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val furniture = furnitureList[position]
        holder.furnitureName.text = furniture.name
        holder.furniturePrice.text = "$${furniture.price}"
        holder.furnitureDetails.text = furniture.details

        Glide.with(holder.itemView.context)
            .load(furniture.image)
            .into(holder.furnitureImage)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FurnitureActivity::class.java)
            intent.putExtra("furniture", furniture)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = furnitureList.size
}
