package com.tobidaada.chatapp.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.ui.start.StartActivity

class MainActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
    }


    override fun onStart() {
        super.onStart()

        // Check if the user is signed in (non-null) and update UI accordingly
        val currentUser = mAuth.currentUser

        if (currentUser == null) {
            val startIntent = Intent(this@MainActivity, StartActivity::class.java)
            startActivity(startIntent)
            finish()
        }

    }
}
