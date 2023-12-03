package com.example.project_final

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class login_admin_activity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signInBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        email = findViewById(R.id.email_et)
        password = findViewById(R.id.password_et)
        signInBtn = findViewById(R.id.btn_sign_in)

        signInBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email1 = email.text.toString()
        val password1 = password.text.toString()

        if (!TextUtils.isEmpty(email1) && !TextUtils.isEmpty(password1)) {
            if (email1 == "admin" && password1 == "Admin") {
                val intent = Intent(this, admin_panel_activity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please enter correct Email and Password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter Email and password", Toast.LENGTH_SHORT).show()
        }
    }
}
