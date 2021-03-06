package com.tobidaada.chatapp.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.ui.status.StatusActivity
import com.tobidaada.chatapp.utils.gone
import com.tobidaada.chatapp.utils.toast
import com.tobidaada.chatapp.utils.visible
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        val SELECT_IMAGE = 1000
    }

    private lateinit var mUserDatabase: DatabaseReference
    private lateinit var mCurrentUser: FirebaseUser
    private lateinit var mStorageReference: StorageReference

    private lateinit var mNameTextView: TextView
    private lateinit var mStatusTextView: TextView
    private lateinit var mCircleImageView: CircleImageView
    private lateinit var mChangeStatusButton: Button
    private lateinit var mChangeImageButton: Button
    private lateinit var mProgressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mNameTextView = settings_display_name_tv
        mStatusTextView = settings_status_tv
        mCircleImageView = settings_image
        mChangeStatusButton = settings_change_status_btn
        mChangeImageButton = settings_change_image_btn
        mProgressBar = settings_progressbar

        mChangeImageButton.setOnClickListener { onChangeImageButtonClicked() }
        mChangeStatusButton.setOnClickListener { onChangeStatusSelected() }

        mStorageReference = FirebaseStorage.getInstance().reference

        mCurrentUser = FirebaseAuth.getInstance().currentUser!!

        val currentUserId = mCurrentUser.uid

        mUserDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(currentUserId)

        mUserDatabase.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                // Happens when an error occurs during the data retrieval process
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Happens when ever we retrieve data
                val name = dataSnapshot.child("name").value.toString()
                val image = dataSnapshot.child("image").value.toString()
                val status = dataSnapshot.child("status").value.toString()
                val thumb_image = dataSnapshot.child("thumb_image").value.toString()

                mNameTextView.text = name
                mStatusTextView.text = status

                Glide.with(this@SettingsActivity)
                        .load(image)
                        .into(settings_image)
            }

        })
    }

    private fun onChangeImageButtonClicked() {
        val galleryIntent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT

        }

        startActivityForResult(galleryIntent, SELECT_IMAGE)

    }

    private fun onChangeStatusSelected() {
        val intent = Intent(this@SettingsActivity, StatusActivity::class.java).apply {
            putExtra("userStatus", mStatusTextView.text.toString())
        }
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                val uri = data.data

                CropImage.activity(uri)
                        .setAspectRatio(1, 1)
                        .start(this)
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                val imageName = mCurrentUser.uid
                val filePath = mStorageReference.child("profile_images").child("$imageName.jpg")

                val uploadTask = filePath.putFile(resultUri)

                mProgressBar.visible()

                uploadTask
                        .continueWithTask {
                            task ->
                            if (!task.isSuccessful) {
                                // Something went wrong
                            }

                            filePath.downloadUrl
                        }
                        .addOnCompleteListener {
                            task ->
                            mProgressBar.gone()

                            if (task.isSuccessful) {
                                this@SettingsActivity.toast("Image Upload Successful")
                                val downloadUrl = task.result.toString()
                                mUserDatabase.child("image").setValue(downloadUrl)

                            } else {
                                // TODO: Add Error handler
                            }
                        }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val exception = result.error
            }
        }

    }
}
