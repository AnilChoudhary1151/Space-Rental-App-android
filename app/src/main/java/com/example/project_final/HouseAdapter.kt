package com.example.project_final

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HouseAdapter(private val houseList: List<House1>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(house: House1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_house, parent, false)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val house = houseList[position]
        holder.bind(house)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(house)
        }
    }

    override fun getItemCount(): Int {
        return houseList.size
    }

    class HouseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
        private val textViewRent: TextView = itemView.findViewById(R.id.textViewRent)

        fun bind(house: House1) {
            Glide.with(itemView.context)
                .load(house.imageUrl)
                .into(imageView)

            textViewName.text = house.name
            textViewNumber.text = house.number
            textViewRent.text = house.rent
        }
    }
}
