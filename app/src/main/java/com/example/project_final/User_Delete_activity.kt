package com.example.project_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class User_Delete_activity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewNumber: TextView
    private lateinit var textViewRent: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonConfirm: Button

    private val houseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("House List")
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_delete)

        imageView = findViewById(R.id.imageViewde)
        textViewName = findViewById(R.id.textViewNamede)
        textViewNumber = findViewById(R.id.textViewNumberde)
        textViewRent = findViewById(R.id.textViewRentde)
        progressBar = findViewById(R.id.progressBarBookingde)
        buttonConfirm = findViewById(R.id.buttonConfirmde)

        val houseId = intent.getStringExtra("id") ?: ""
        val email = intent.getStringExtra("email") ?: ""

        showLoadingScreen()

        fetchHouseDetailsFromHouseList(houseId) { house ->
            hideLoadingScreen()

            house?.let {
                // Display house details
                Glide.with(this)
                    .load(it.imageUrl)
                    .into(imageView)

                textViewName.text = "Name: ${it.name}"
                textViewNumber.text = "Number: ${it.number}"
                textViewRent.text = "Rent: ${it.rent}"

                // Set click listener for the Confirm button
                buttonConfirm.setOnClickListener {
                    // Handle booking cancellation here
                    // For example, you can delete the booking entry from the database
                    deleteBooking(email, houseId)
                }
            }
        }
    }

    private fun showLoadingScreen() {
        progressBar.visibility = View.VISIBLE
        setDetailsVisibility(View.GONE)
    }

    private fun hideLoadingScreen() {
        progressBar.visibility = View.GONE
        setDetailsVisibility(View.VISIBLE)
    }

    private fun setDetailsVisibility(visibility: Int) {
        imageView.visibility = visibility
        textViewName.visibility = visibility
        textViewNumber.visibility = visibility
        textViewRent.visibility = visibility
        buttonConfirm.visibility = visibility
    }

    private fun fetchHouseDetailsFromHouseList(houseId: String, onComplete: (House1?) -> Unit) {
        houseReference.child(houseId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val house = dataSnapshot.getValue(House1::class.java)
                onComplete(house)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onComplete(null)
            }
        })
    }

    private fun deleteBooking(email: String, houseId: String) {
        // Perform the deletion of the booking entry in the database
        databaseReference.child(houseId).removeValue()

        // Display a message or handle the UI as needed
        // For example, you can show a Toast message
        Toast.makeText(this, "Booking deleted", Toast.LENGTH_LONG).show()

        // Finish the activity
        finish()
    }
}
