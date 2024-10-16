package com.example.homedesign.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import android.Manifest
import com.example.homedesign.R
import com.example.homedesign.activities.BaseActivity
import com.example.homedesign.firebase.FirestoreClass
import com.example.homedesign.models.User
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException

class MyProfileActivity : BaseActivity() {

    private var mSelectedImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        setupActionBar()

        FirestoreClass().signInUser(this@MyProfileActivity)

        val ivUserImage: CircleImageView = findViewById(R.id.iv_profile_user_image)

        ivUserImage.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {

                showImageChooser()

            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            // The uri of selection image from phone storage.
            mSelectedImageFileUri = data.data

            try {
                val ivUserImage: CircleImageView = findViewById(R.id.iv_profile_user_image)

                // Load the user image in the ImageView.
                Glide
                    .with(this@MyProfileActivity)
                    .load(Uri.parse(mSelectedImageFileUri.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.ic_user_place_holder) // A default place holder
                    .into(ivUserImage) // the view in which the image will be loaded.
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * This function will identify the result of runtime permission after the user allows or deny permission based on the unique code.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * A function for user profile image selection from phone storage.
     */
    private fun showImageChooser() {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    /**
     * A companion object to declare the constants.
     */
    companion object {
        //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult
        private const val READ_STORAGE_PERMISSION_CODE = 1

        // TODO (Step 6: Add a constant for image selection from phone storage)
        // START
        private const val PICK_IMAGE_REQUEST_CODE = 2
        // END
        const val READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"
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


    fun setUserDataInUI(user: User) {
        val ivUserImage: CircleImageView = findViewById(R.id.iv_profile_user_image)
        Glide
            .with(this@MyProfileActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(ivUserImage)

        val etName: EditText = findViewById(R.id.et_name)
        val etEmail: EditText = findViewById(R.id.et_email)
        val etMobile: EditText = findViewById(R.id.et_mobile)


        etName.setText(user.name)
        etEmail.setText(user.email)
        if (user.mobile != 0L) {
            etMobile.setText(user.mobile.toString())
        }
    }

    private fun getDeclaredPermissions(): List<String> {
        return try {
            val packageInfo = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_PERMISSIONS
            )
            packageInfo.requestedPermissions?.toList() ?: emptyList()
        } catch (e: PackageManager.NameNotFoundException) {
            emptyList()
        }
    }

    private fun findPermission(permissionName: String): String? {
        val declaredPermissions = getDeclaredPermissions()
        return if (declaredPermissions.contains(permissionName)) permissionName else null
    }
}
