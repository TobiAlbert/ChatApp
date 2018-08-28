package com.tobidaada.chatapp.ui.settings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.tobidaada.chatapp.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var mUserDatabase: DatabaseReference
    private lateinit var mCurrentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mCurrentUser = FirebaseAuth.getInstance().currentUser!!

        val currentUserId = mCurrentUser.uid

        mUserDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(currentUserId)

        mUserDatabase.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                // Happens when an error occurs during the data retrieval process
            }

            override fun onDataChange(p0: DataSnapshot) {
                // Happens when ever we retrieve data
            }

        })
    }
}
