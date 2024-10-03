package com.example.homedesign.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.homedesign.R

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()
    }

    private fun setupActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_sign_up_activity)

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val buttonSignUp : Button = findViewById(R.id.btn_sign_up)
        buttonSignUp.setOnClickListener() {
            registerUser()
        }
    }

    private fun registerUser() {
        val et_name: EditText = findViewById(R.id.et_name)
        val et_email: EditText = findViewById(R.id.et_email)
        val et_password: EditText = findViewById(R.id.et_password)
        val name : String = et_name.text.toString().trim { it <= ' '}
        val email : String = et_email.text.toString().trim { it <= ' '}
        val password : String = et_password.text.toString().trim { it <= ' '}

        if(validateForm(name, email, password)){
            Toast.makeText(this@SignUpActivity,
                "Now we can register a new user.",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun validateForm(name: String, email : String, password: String) : Boolean {
        return when {
            TextUtils.isEmpty(name) ->{
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) ->{
                showErrorSnackBar("Please enter a email")
                false
            }
            TextUtils.isEmpty(password) ->{
                showErrorSnackBar("Please enter a password")
                false
            } else -> {
                true
            }
        }
    }
}