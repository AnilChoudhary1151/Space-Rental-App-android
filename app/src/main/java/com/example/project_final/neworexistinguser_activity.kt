package com.example.project_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class neworexistinguser_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neworexistinguser)

        val cvnewuser: CardView = findViewById(R.id.cv_newuser)
        val cvexisting: CardView = findViewById(R.id.cv_existinguser)

        cvnewuser.setOnClickListener {
            val intentLoginAdmin = Intent(this, registeruser_activity::class.java)
            startActivity(intentLoginAdmin)
        }

        cvexisting.setOnClickListener {
            val intentLoginUser = Intent(this, loginUser_activity::class.java)
            startActivity(intentLoginUser)
        }
    }
}