package com.example.project_final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class sucessfullyRegister_activity : AppCompatActivity() {

    private lateinit var profile_image: ImageView
    private lateinit var profile_email: TextView
    private lateinit var btn_sign_out: Button
    private lateinit var btn_continue: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucessfully_register)

        mAuth = FirebaseAuth.getInstance()

        profile_image = findViewById(R.id.profile_image)
        profile_email = findViewById(R.id.profile_email)
        btn_sign_out = findViewById(R.id.btn_sign_out)
        btn_continue = findViewById(R.id.btn_continue)

        val currentUser: FirebaseUser? = mAuth.currentUser

        if (currentUser != null) {
            // Set the retrieved name and email
            profile_email.text = currentUser.email
        }

        btn_sign_out.setOnClickListener {
            // Sign out logic
            mAuth.signOut()
            finish()
        }

        btn_continue.setOnClickListener {
            // Continue logic
            // You can choose either neworexistinguser_activity or selectCity_activity based on your condition
            // Example:
            val intent = Intent(this, user_panel_activity::class.java)
            intent.putExtra("email", profile_email.text.toString())
            startActivity(intent)
            finish()
        }
    }
}
