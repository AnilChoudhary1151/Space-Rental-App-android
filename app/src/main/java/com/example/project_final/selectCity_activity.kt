package com.example.project_final

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class selectCity_activity : AppCompatActivity() {

    private lateinit var cvMumbai: CardView
    private lateinit var cvHyderabad: CardView
    private lateinit var cvChennai: CardView
    private lateinit var cvBangalore: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        cvMumbai = findViewById(R.id.cv_img_mumbai)
        cvHyderabad = findViewById(R.id.cv_img_hyderabad)
        cvChennai = findViewById(R.id.cv_img_chennai)
        cvBangalore = findViewById(R.id.cv_img_banglore)

        // Retrieve the email from the intent
        val email: String = intent.getStringExtra("email").toString()

        // Set a click listener for the Mumbai CardView
        cvMumbai.setOnClickListener {
            handleCardClick("Mumbai", email)
        }

        // Set a click listener for the Hyderabad CardView
        cvHyderabad.setOnClickListener {
            handleCardClick("Hyderabad", email)
        }

        // Set a click listener for the Chennai CardView
        cvChennai.setOnClickListener {
            handleCardClick("Chennai", email)
        }

        // Set a click listener for the Bangalore CardView
        cvBangalore.setOnClickListener {
            handleCardClick("Bangalore", email)
        }
    }

    private fun handleCardClick(city: String, email: String) {
        val intent = Intent(this, user_selectland_activity::class.java)
        intent.putExtra("city", city)
        intent.putExtra("email", email)
        startActivity(intent)
    }
}
