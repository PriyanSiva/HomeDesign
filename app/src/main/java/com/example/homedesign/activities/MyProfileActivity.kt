package com.example.homedesign.activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.homedesign.R
import com.example.homedesign.activities.BaseActivity

class MyProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        setupActionBar()
    }

    private fun setupActionBar() {
        val profileToolBar: Toolbar = findViewById(R.id.toolbar_my_profile_activity);
        setSupportActionBar(profileToolBar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        profileToolBar.setNavigationOnClickListener { onBackPressed() }
    }
}
