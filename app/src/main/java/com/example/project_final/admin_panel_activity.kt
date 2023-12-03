package com.example.project_final

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class admin_panel_activity : AppCompatActivity() {
    private lateinit var addHouse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        addHouse = findViewById(R.id.addhousebtn)
        val intent = Intent(this, admin_AddHouse_activity::class.java)
        addHouse.setOnClickListener {
        startActivity(intent)
             }
          }
       }
