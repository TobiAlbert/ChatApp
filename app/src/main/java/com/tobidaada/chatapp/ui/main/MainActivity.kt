package com.tobidaada.chatapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.ui.start.StartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mToolbar = main_toolbar as Toolbar

        setSupportActionBar(mToolbar)
        supportActionBar?.title = "ChatApp"
    }

    override fun onStart() {
        super.onStart()

        // Check if the user is signed in (non-null) and update UI accordingly
        val currentUser = mAuth.currentUser

        if (currentUser == null) {
            sendToStartActivity()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout_menu -> logoutUser()
            R.id.all_users_menu -> getAllUsers()
            R.id.account_settings_menu -> accountSettings()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun sendToStartActivity() {
        val startIntent = Intent(this@MainActivity, StartActivity::class.java)
        startActivity(startIntent)
        finish()
    }

    private fun logoutUser() {
        mAuth.signOut()

        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            sendToStartActivity()
        }
    }

    private fun getAllUsers() {}

    private fun accountSettings() {}
}
