package com.example.homedesign.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homedesign.R
import com.example.homedesign.adapters.FurnitureAdapter
import com.example.homedesign.models.Furniture
import com.example.homedesign.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FurnitureActivity : AppCompatActivity() {
    private lateinit var furnitureAdapter: FurnitureAdapter
    private lateinit var recyclerView: RecyclerView
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_furniture)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadFurniture("Bedroom") // Default loading
    }

    private fun loadFurniture(roomType: String) {
        CoroutineScope(Dispatchers.Main).launch {
            db.collection(Constants.FURNITURE)
                .whereEqualTo("roomType", roomType)
                .get()
                .addOnSuccessListener { documents ->
                    val furnitureList = documents.map { document ->
                        document.toObject(Furniture::class.java).copy(id = document.id)
                    }
                    furnitureAdapter = FurnitureAdapter(furnitureList)
                    recyclerView.adapter = furnitureAdapter
                }
                .addOnFailureListener { exception ->
                    // Handle the error
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_furniture_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_bedroom -> {
                loadFurniture("Bedroom")
                true
            }
            R.id.menu_living_room -> {
                loadFurniture("Living Room")
                true
            }
            R.id.menu_kitchen -> {
                loadFurniture("Kitchen")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
