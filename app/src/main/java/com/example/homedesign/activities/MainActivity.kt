package com.example.homedesign.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.homedesign.R
import com.example.homedesign.activities.BaseActivity
import com.example.homedesign.firebase.FirestoreClass
import com.example.homedesign.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setActionBar()

//        FirebaseApp.initializeApp(this)

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        val buttonFurniture: Button = findViewById(R.id.button_furniture)
        buttonFurniture.setOnClickListener {
            val intent = Intent(this, FurnitureActivity::class.java)
            startActivity(intent)
        }

        val firestoreClass = FirestoreClass()
        firestoreClass.signInUser(this)

//        uploadJsonData(jsonData)

    }

    private fun setActionBar() {
        val toolBarMainActivity: Toolbar = findViewById(R.id.toolbar_main_activity)
        setSupportActionBar(toolBarMainActivity)
        toolBarMainActivity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolBarMainActivity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    private fun toggleDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            doubleBackToExit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                startActivity(Intent(this, MyProfileActivity::class.java))
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun updateNavigationUserDetails(user: User){
        val userImageView: CircleImageView = findViewById(R.id.iv_user_image)
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(userImageView)

        val username: TextView = findViewById(R.id.tv_username)
        username.text = user.name
    }

    private val jsonData = """{
        "furniture1": {
        "name": "Sofa Set",
        "price": 799.99,
        "image": "https://example.com/images/sofa_set.jpg",
        "details": "A comfortable 5-seater sofa set with plush cushions.",
        "roomType": "Living Room"
    },
        "furniture2": {
        "name": "Dining Table",
        "price": 499.99,
        "image": "https://example.com/images/dining_table.jpg",
        "details": "A modern wooden dining table that seats six.",
        "roomType": "Kitchen"
    },
        "furniture3": {
        "name": "Queen Bed",
        "price": 599.99,
        "image": "https://example.com/images/queen_bed.jpg",
        "details": "A stylish queen-size bed with a soft headboard.",
        "roomType": "Bedroom"
    },
        "furniture4": {
        "name": "Office Desk",
        "price": 299.99,
        "image": "https://example.com/images/office_desk.jpg",
        "details": "A spacious desk ideal for work or study.",
        "roomType": "Study Room"
    },
        "furniture5": {
        "name": "Bookshelf",
        "price": 199.99,
        "image": "https://example.com/images/bookshelf.jpg",
        "details": "A tall bookshelf with multiple shelves for books and decor.",
        "roomType": "Study Room"
    },
        "furniture6": {
        "name": "Coffee Table",
        "price": 149.99,
        "image": "https://example.com/images/coffee_table.jpg",
        "details": "A chic coffee table perfect for the living room.",
        "roomType": "Living Room"
    },
        "furniture7": {
        "name": "Kitchen Island",
        "price": 399.99,
        "image": "https://example.com/images/kitchen"
    }}"""

        private fun uploadJsonData(jsonString: String) {
        val db = FirebaseFirestore.getInstance()
        val jsonObject = JSONObject(jsonString)

        for (key in jsonObject.keys()) {
            val data = jsonObject.getJSONObject(key).toMap() // Convert to a Map
            db.collection("FURNITURE") // Change this to your collection name
                .document(key)
                .set(data)
                .addOnSuccessListener {
                    println("DocumentSnapshot successfully written for $key!")
                }
                .addOnFailureListener { e ->
                    println("Error writing document for $key: $e")
                }
        }
    }

    private fun JSONObject.toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        val keys = this.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            map[key] = this.get(key)
        }
        return map
    }

}
