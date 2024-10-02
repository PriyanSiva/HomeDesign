package com.example.homedesign.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.homedesign.R


class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val signUpButton: Button = findViewById(R.id.btn_sign_up_intro)

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        val signInButton: Button = findViewById(R.id.btn_sign_in_intro)

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }


}