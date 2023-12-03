// BookingAdapter.kt
package com.example.project_final

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class BookingAdapter(private val bookingList: List<Booking>) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    private val houseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("House List")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_house, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookingList[position]

        // Fetch and display house details
        fetchHouseDetails(holder, booking.houseId)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
        val textViewRent: TextView = itemView.findViewById(R.id.textViewRent)
    }

    private fun fetchHouseDetails(holder: ViewHolder, uniqueId: String) {
        houseReference.child(uniqueId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val house = dataSnapshot.getValue(House1::class.java)
                house?.let {
                    Glide.with(holder.itemView.context)
                        .load(it.imageUrl)
                        .into(holder.imageView)

                    holder.textViewName.text = it.name
                    holder.textViewNumber.text = it.number
                    holder.textViewRent.text = it.rent
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}
