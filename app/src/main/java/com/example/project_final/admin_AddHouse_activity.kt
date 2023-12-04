package com.example.project_final

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class admin_AddHouse_activity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var number: EditText
    private lateinit var rent: EditText
    private lateinit var citySpinner: Spinner
    private lateinit var uploadImageButton: ImageButton
    private lateinit var addHouseBtn: Button

    private val cities = arrayOf("Mumbai", "Chennai", "Hyderabad", "Bangalore")

    private lateinit var selectedCity: String

    private val db = FirebaseDatabase.getInstance()
    private val reference = db.getReference().child("House List")

    private val storage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference.child("images")

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            uploadImageButton.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.verify_green)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_house)

        name = findViewById(R.id.ownernameet)
        number = findViewById(R.id.ownernumberet)
        rent = findViewById(R.id.rentamountet)
        citySpinner = findViewById(R.id.citySpinner)
        uploadImageButton = findViewById(R.id.uploadImageButton)
        addHouseBtn = findViewById(R.id.addHouseBtn)

        // Set up the city spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter

        // Set up city spinner listener
        citySpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                selectedCity = cities[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing here
            }
        })

        // Set up image upload button listener
        uploadImageButton.setOnClickListener {
            // Open gallery to select an image
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getContent.launch(intent)
        }

        // Set up add house button listener
        addHouseBtn.setOnClickListener {
            addHouseBtn.isEnabled=false
            val mname = name.text.toString()
            val mnumber = number.text.toString()
            val mrent = rent.text.toString()

            if (mname.isEmpty() || mnumber.isEmpty() || mrent.isEmpty() || selectedCity.isEmpty() || imageUri == null) {
                // Show error message if any field is empty
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                // Generate a unique ID for the house entry
                val houseId = reference.push().key

                if (houseId != null) {
                    // Upload image to Firebase Storage
                    val imageRef = storageReference.child("$houseId.jpg")
                    val uploadTask: UploadTask = imageRef.putFile(imageUri!!)

                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        imageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            val house1 = House1(null,mname, mnumber, mrent, selectedCity, downloadUri.toString())
                            reference.child(houseId).setValue(house1)

                            // Show success message
                            Toast.makeText(this, "House added successfully", Toast.LENGTH_LONG).show()
                            finish()
                            // Reset all fields
//                            name.setText("")
//                            number.setText("")
//                            rent.setText("")
//                            citySpinner.setSelection(0)
//                            uploadImageButton.setImageResource(R.drawable.upload)
//
//                            // Clear the imageUri
//                            imageUri = null
                        }
                    }
                }
            }
        }
    }
}
