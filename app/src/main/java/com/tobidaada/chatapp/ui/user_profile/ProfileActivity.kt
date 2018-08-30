package com.tobidaada.chatapp.ui.user_profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.utils.disableViews
import com.tobidaada.chatapp.utils.enableViews
import com.tobidaada.chatapp.utils.gone
import com.tobidaada.chatapp.utils.visible
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var mUserDatabaseRef: DatabaseReference

    private lateinit var mProfileImageView: ImageView
    private lateinit var mProfileNameTextView: TextView
    private lateinit var mProfileStatusTextView: TextView
    private lateinit var mSendRequestButton: Button
    private lateinit var mProgressBar:  ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mProfileImageView = profile_image_view
        mProfileNameTextView = profile_name_tv
        mProfileStatusTextView = profile_statuc_tv
        mSendRequestButton = profile_send_request_btn
        mProgressBar = profile_progress_bar


        val startIntent = intent

        if (startIntent == null) {
            onBackPressed()
        }

        if (!startIntent.hasExtra("userId")) {
            onBackPressed()
        }

        val userId = startIntent.getStringExtra("userId")

        mProgressBar.visible()
        disableViews(arrayOf(mProfileImageView,
                mSendRequestButton,
                mProfileStatusTextView,
                this.mProfileNameTextView))

        mUserDatabaseRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

        mUserDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.child("name").value.toString()
                val status = dataSnapshot.child("status").value.toString()
                val image = dataSnapshot.child("image").value.toString()

                mProfileNameTextView.text = name
                mProfileStatusTextView.text = status

                if (image != "default") {
                    Glide.with(this@ProfileActivity)
                            .load(image)
                            .into(mProfileImageView)
                }

                mProgressBar.gone()
                enableViews(arrayOf(mProfileImageView,
                        mSendRequestButton,
                        mProfileStatusTextView,
                        mProfileNameTextView))
            }

        })


    }
}
