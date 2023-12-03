package com.example.project_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class TypeOfUser_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type_of_user)

        val cvAdmin: CardView = findViewById(R.id.cv_admin)
        val cvUser: CardView = findViewById(R.id.cv_user)

        cvAdmin.setOnClickListener {
            val intentLoginAdmin = Intent(this, login_admin_activity::class.java)
            startActivity(intentLoginAdmin)
        }

        cvUser.setOnClickListener {
            val intentLoginUser = Intent(this, neworexistinguser_activity::class.java)
            startActivity(intentLoginUser)
        }

    }
}