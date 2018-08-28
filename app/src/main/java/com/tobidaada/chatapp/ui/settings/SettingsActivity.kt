package com.tobidaada.chatapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.ui.status.StatusActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        val TAG = SettingsActivity::class.java.simpleName
    }

    private lateinit var mUserDatabase: DatabaseReference
    private lateinit var mCurrentUser: FirebaseUser
    private lateinit var mNameTextView: TextView
    private lateinit var mStatusTextView: TextView
    private lateinit var mCircleImageView: CircleImageView
    private lateinit var mChangeStatusButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mNameTextView = settings_display_name_tv
        mStatusTextView = settings_status_tv
        mCircleImageView = settings_image
        mChangeStatusButton = settings_change_status_btn

        mChangeStatusButton.setOnClickListener { onChangeStatusSelected() }

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


            }

        })
    }

    private fun onChangeStatusSelected() {
        val intent = Intent(this@SettingsActivity, StatusActivity::class.java).apply {
            putExtra("userStatus", mStatusTextView.text.toString())
        }
        startActivity(intent)
    }
}
