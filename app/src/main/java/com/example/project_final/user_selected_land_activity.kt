package com.example.project_final

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class user_selected_land_activity : AppCompatActivity() {

    private lateinit var buttonConfirm: Button
    private lateinit var progressBarBooking: ProgressBar

    private lateinit var userEmail: String
    private lateinit var houseId: String

    private lateinit var textViewName: TextView
    private lateinit var textViewNumber: TextView
    private lateinit var textViewRent: TextView
    private lateinit var textViewCity: TextView
    private lateinit var imageView: ImageView

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_selected_land)

        // Retrieve data from the intent
        userEmail = intent.getStringExtra("email") ?: ""
        houseId = intent.getStringExtra("id") ?: ""

        textViewName = findViewById(R.id.textViewName)
        textViewNumber = findViewById(R.id.textViewNumber)
        textViewRent = findViewById(R.id.textViewRent)
        textViewCity = findViewById(R.id.textViewCity)
        imageView = findViewById(R.id.imageView)

        buttonConfirm = findViewById(R.id.buttonConfirm)
        progressBarBooking = findViewById(R.id.progressBarBooking)

        buttonConfirm.setOnClickListener {
            showBookingProgressBar()
            confirmBooking()
        }

        // Load house details
        loadHouseDetails()
    }

    private fun loadHouseDetails() {
        // Retrieve house details from the intent
        val name = intent.getStringExtra("name") ?: ""
        val number = intent.getStringExtra("number") ?: ""
        val rent = intent.getStringExtra("rent") ?: ""
        val city = intent.getStringExtra("city") ?: ""
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""

        // Update TextViews with house details
        textViewName.text = "Name: ${name}"
        textViewNumber.text = "Number: ${number}"
        textViewRent.text = "Rent: Rs${rent}"
        textViewCity.text = "City: ${city}"

        // Load and display the image
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        // Check booking status and update button text and color
        checkBookingStatus()
    }

    private fun checkBookingStatus() {
        databaseReference.child(houseId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    hideBookingProgressBar()
                    // Show message: "Already booked, please call the owner for details"
                    showToast("Already booked, please call the owner for details")
                    // Update button text and color for already booked
                    buttonConfirm.text = "Booked"
                    buttonConfirm.setBackgroundResource(R.color.verify_green)
                    buttonConfirm.isEnabled = false

                } else {
                    // If not booked, set button to "Confirm Booking" and enable it
                    buttonConfirm.text = "Confirm Booking"
                    buttonConfirm.isEnabled = true
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                hideBookingProgressBar()
                // Handle error
            }
        })
    }

    private fun confirmBooking() {
        val bookingMap = HashMap<String, Any>()
        bookingMap["email"] = userEmail

        databaseReference.child(houseId).setValue(bookingMap).addOnCompleteListener { task ->
            hideBookingProgressBar()
            if (task.isSuccessful) {
                // Show success message
                showToast("Successfully booked!")

                // Update button text and color after successful booking
                buttonConfirm.text = "Booked"
                buttonConfirm.setBackgroundResource(R.color.verify_green)
                buttonConfirm.isEnabled = false

            } else {
                // Show error message: "Booking failed, please try again"
                showToast("Booking failed, please try again")
                // Implement your logic here
            }
        }
    }

    private fun showBookingProgressBar() {
        progressBarBooking.visibility = View.VISIBLE
        buttonConfirm.isEnabled = false
    }

    private fun hideBookingProgressBar() {
        progressBarBooking.visibility = View.GONE
        buttonConfirm.isEnabled = true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
