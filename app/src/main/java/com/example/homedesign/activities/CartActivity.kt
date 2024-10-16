package com.example.homedesign.activities

import CartAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homedesign.R
import com.example.homedesign.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : BaseActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val rvCartItems: RecyclerView = findViewById(R.id.rvCartItems)
        rvCartItems.layoutManager = LinearLayoutManager(this)

        loadCartItems { cartItems ->
            cartAdapter = CartAdapter(cartItems)
            rvCartItems.adapter = cartAdapter
        }
    }

    private fun loadCartItems(onComplete: (List<CartItem>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("cart")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val cartItems = result.toObjects(CartItem::class.java)
                onComplete(cartItems)
            }
            .addOnFailureListener { e ->
                // Handle the error
            }
    }
}
