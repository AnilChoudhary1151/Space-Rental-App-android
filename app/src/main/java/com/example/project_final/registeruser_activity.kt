package com.example.project_final

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class registeruser_activity : AppCompatActivity() {

    private lateinit var userFullName: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var userRePassword: EditText
    private lateinit var userContact: EditText
    private lateinit var btnRegister: Button

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeruser)

        userFullName = findViewById(R.id.id_fullname_newuser)
        userEmail = findViewById(R.id.id_email_newuser)
        userPassword = findViewById(R.id.id_password_newuser)
        userRePassword = findViewById(R.id.id_repassword_newuser)
        userContact = findViewById(R.id.id_mobileno_newuser)
        btnRegister = findViewById(R.id.btn_regitser)

        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("userData")

        btnRegister.setOnClickListener {
            val name = userFullName.text.toString()
            val email = userEmail.text.toString()
            val password = userPassword.text.toString()
            val rePassword = userRePassword.text.toString()
            val mobileNo = userContact.text.toString()

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword) || TextUtils.isEmpty(mobileNo)) {
                Toast.makeText(this, "Please Add some data", Toast.LENGTH_SHORT).show()
            } else if (password != rePassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                registerUserWithEmailAndPassword(email, password, name, mobileNo)
            }
        }
    }

    private fun registerUserWithEmailAndPassword(email: String, password: String, name: String, mobileNo: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    firebaseUser = FirebaseAuth.getInstance().currentUser!!

                    val userData = UserData(name, email, mobileNo, password)

                    // Use email as a unique identifier
                    val emailAsKey = encodeEmailAsFirebaseKey(email)
                    databaseReference.child(emailAsKey).setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this@registeruser_activity, "Successfully Registered", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, sucessfullyRegister_activity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this@registeruser_activity, "Registration Failed $e", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this@registeruser_activity, "Registration Failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun encodeEmailAsFirebaseKey(email: String): String {
        return email.replace(".", "_")
    }
}
