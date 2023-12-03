package com.example.project_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class Splash_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val txtAppName: TextView = findViewById(R.id.id_splash_app_name)
        val splashTagline: TextView = findViewById(R.id.tagline)

        val alphaOnAppName: Animation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation)
        val translateOnTagline: Animation = AnimationUtils.loadAnimation(this, R.anim.translate_animation)

        txtAppName.startAnimation(alphaOnAppName)
        splashTagline.startAnimation(translateOnTagline)

        // Handler for - Splash Screen Running Time
        Handler(Looper.getMainLooper()).postDelayed({
            val selectRole = Intent(this, TypeOfUser_activity::class.java)
            startActivity(selectRole)
            finish()
        }, 5000)
    }
}