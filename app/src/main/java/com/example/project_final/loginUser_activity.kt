package com.example.project_final

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class loginUser_activity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var linkToRegisterTextView: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        mAuth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.email_et)
        passwordEditText = findViewById(R.id.password_et)
        signInButton = findViewById(R.id.btn_sign_in)
        linkToRegisterTextView = findViewById(R.id.id_linkto_register)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                signInUser(email, password)
            }
        }

        linkToRegisterTextView.setOnClickListener {
            val intent = Intent(this, registeruser_activity::class.java)
            startActivity(intent)
        }
    }

    private fun signInUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    // Pass email to the next activity
                    val intent = Intent(this, sucessfullyRegister_activity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this, "Authentication failed. Check email and password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
