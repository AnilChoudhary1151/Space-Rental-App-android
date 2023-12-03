package com.example.project_final

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class orderslist_activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingAdapter: BookingAdapter
    private lateinit var bookingList: MutableList<Booking>
    private lateinit var progressBar: ProgressBar

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Bookings")
    private val houseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("House List")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderslist)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        bookingList = mutableListOf()
        bookingAdapter = BookingAdapter(bookingList)

        recyclerView.adapter = bookingAdapter

        // Retrieve user email from the intent
        val userEmail = intent.getStringExtra("email") ?: ""

        // Initialize the UI and fetch data
        initializeUI(userEmail)

        // Set click listener for RecyclerView items
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // Get the clicked booking
                        val clickedBooking = bookingList[position]

                        // Start HouseDetailsActivity with details
                        val intent = Intent(this@orderslist_activity, User_Delete_activity::class.java).apply {
                            putExtra("id", clickedBooking.houseId)
                            putExtra("email", clickedBooking.email)
                            putExtra("imageUrl", clickedBooking.imageUrl)
                            putExtra("name", clickedBooking.name)
                            putExtra("number", clickedBooking.number)
                            putExtra("rent", clickedBooking.rent)
                        }
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        // Handle long item click if needed
                    }
                })
        )
    }

    private fun initializeUI(userEmail: String) {
        showLoadingScreen()

        // Query the database to get all bookings for the user's email
        databaseReference.orderByChild("email").equalTo(userEmail)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    hideLoadingScreen()

                    val bookingCount = dataSnapshot.childrenCount
                    var fetchCount = 0

                    for (bookingSnapshot in dataSnapshot.children) {
                        val bookingId = bookingSnapshot.key.orEmpty()
                        fetchHouseDetails(bookingId) { house ->
                            house?.let {
                                val booking = Booking(
                                    houseId = bookingId,
                                    email = userEmail,
                                    name = house.name.orEmpty(),
                                    number = house.number.orEmpty(),
                                    rent = house.rent.orEmpty(),
                                    imageUrl = house.imageUrl.orEmpty()
                                )
                                bookingList.add(booking)

                                fetchCount++
                                if (fetchCount.toLong() == bookingCount) {
                                    // Notify the adapter only when all fetch operations are complete
                                    bookingAdapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    hideLoadingScreen()
                    // Handle error
                }
            })
    }

    private fun showLoadingScreen() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideLoadingScreen() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun fetchHouseDetails(bookingId: String, onComplete: (House1?) -> Unit) {
        databaseReference.child(bookingId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val booking = dataSnapshot.getValue(Booking::class.java)
                booking?.let {
                    val houseId = dataSnapshot.key // Use the unique ID as the house ID

                    fetchHouseDetailsFromHouseList(houseId.orEmpty()) { house ->
                        onComplete(house)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onComplete(null)
            }
        })
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
}
