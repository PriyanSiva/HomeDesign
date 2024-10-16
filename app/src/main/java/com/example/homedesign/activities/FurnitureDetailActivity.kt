package com.example.homedesign.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.homedesign.R
import com.example.homedesign.databinding.ActivityFurnitureDetailBinding
import com.example.homedesign.models.CartItem
import com.example.homedesign.models.Furniture
import com.example.homedesign.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FurnitureDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityFurnitureDetailBinding
    private val mFireStore = FirebaseFirestore.getInstance();
    private var furniture: Furniture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFurnitureDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the furniture object from the intent
        val furniture = intent.getParcelableExtra<Furniture>("furniture")

        furniture?.let {
            binding.furnitureName.text = it.name
            binding.furniturePrice.text = "Price: $${it.price}"
            binding.furnitureDetails.text = it.details
            binding.roomType.text = "Room: ${it.roomType}"

            Glide.with(this)
                .load(it.image)
                .into(binding.furnitureImage)
        } ?: run {
            finish()
        }

        val btnAddToCart: Button = findViewById(R.id.btnAddToCart)

        btnAddToCart.setOnClickListener {
            addToCart()
        }

//        setupActionBar()
    }

    private fun addToCart() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid  // Get the current user's ID
        if (!userId.isNullOrEmpty()) {

            // Create a CartItem object with the furniture data
            val cartItem = CartItem(
                userId = userId,
                furnitureId = furniture!!.id,
                name = furniture!!.name,
                price = furniture!!.price,
                image = furniture!!.image,
                quantity = 1
            )

            // Add the CartItem to Firestore
            mFireStore.collection(Constants.CARTS)
                .add(cartItem)
                .addOnSuccessListener {
                    Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show()
                    goToCart()  // Navigate to CartActivity on success
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to add to cart: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Log.d("UserID", "No user ID found")  // Log an error if the user is not logged in
        }
    }

    private fun goToCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

//    private fun setupActionBar() {
//        val profileToolBar: Toolbar = findViewById(R.id.toolbar_my_furniture_activity);
//        setSupportActionBar(profileToolBar)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
//            actionBar.title = "Furniture Store"
//        }
//
//        profileToolBar.setNavigationOnClickListener { onBackPressed() }
//    }

//    private fun setupActionBar() {
//        // Ensure that the ActionBar is not null before configuring it
//        supportActionBar?.apply {
//            title = "Furniture Details"
//            setDisplayHomeAsUpEnabled(true)  // Enable the back button
//        } ?: run {
//            // Log or handle the case where the ActionBar is null
//            println("ActionBar not available")
//        }
//    }
}