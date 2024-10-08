package com.example.homedesign.activities

import android.os.Bundle
import com.example.homedesign.R
import com.example.homedesign.activities.BaseActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}
