package com.example.project_final

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class user_selectland_activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var houseAdapter: HouseAdapter
    private lateinit var houseList: MutableList<House1>
    private lateinit var progressBar: ProgressBar

    private lateinit var city: String
    private lateinit var userEmail: String

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("House List")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_selectland)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        houseList = mutableListOf()
        houseAdapter = HouseAdapter(houseList, object : HouseAdapter.OnItemClickListener {
            override fun onItemClick(house: House1) {
                // Handle item click, and pass the necessary information to the next activity
                val intent = Intent(this@user_selectland_activity, user_selected_land_activity::class.java)
                intent.putExtra("id", house.id)
                intent.putExtra("imageUrl", house.imageUrl)
                intent.putExtra("name", house.name)
                intent.putExtra("number", house.number)
                intent.putExtra("rent", house.rent)
                intent.putExtra("city", house.city)
                intent.putExtra("email", userEmail)
                startActivity(intent)
            }
        })

        recyclerView.adapter = houseAdapter

        // Retrieve the selected city and user email from the intent
        city = intent.getStringExtra("city") ?: ""
        userEmail = intent.getStringExtra("email") ?: ""

        showLoadingScreen()

        // Query the database to get all houses for the selected city
        databaseReference.orderByChild("city").equalTo(city)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    hideLoadingScreen()

                    for (houseSnapshot in dataSnapshot.children) {
                        val houseId = houseSnapshot.key // Unique ID
                        val house = houseSnapshot.getValue(House1::class.java)
                        house?.let {
                            it.id = houseId
                            houseList.add(it)
                        }
                    }
                    houseAdapter.notifyDataSetChanged()
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
}
