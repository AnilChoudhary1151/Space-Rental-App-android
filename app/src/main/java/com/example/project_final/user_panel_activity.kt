package com.example.project_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class user_panel_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_panel)

        val userBookLandButton: Button = findViewById(R.id.userBookLand)
        val userOrdersButton: Button = findViewById(R.id.userBooked)

        // Retrieve email from the previous activity
        val email = intent.getStringExtra("email") ?: ""

        userBookLandButton.setOnClickListener {
            // Pass the email to the next activity (selectCity_activity)
            val intent = Intent(this, selectCity_activity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }

        userOrdersButton.setOnClickListener {
            // Pass the email to the next activity (orderslist_activity)
            val intent = Intent(this, orderslist_activity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
}
