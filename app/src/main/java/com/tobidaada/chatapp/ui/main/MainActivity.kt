package com.tobidaada.chatapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.R.id.*
import com.tobidaada.chatapp.adapters.MainViewPagerAdapter
import com.tobidaada.chatapp.ui.settings.SettingsActivity
import com.tobidaada.chatapp.ui.start.StartActivity
import com.tobidaada.chatapp.ui.user.UsersActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mToolbar: Toolbar
    private lateinit var mViewPager: ViewPager
    private lateinit var mMainViewPagerAdapter: MainViewPagerAdapter
    private lateinit var mTabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mToolbar = main_toolbar as Toolbar
        mViewPager = main_viewPager
        mTabLayout = main_tab

        setSupportActionBar(mToolbar)
        supportActionBar?.title = "ChatApp"

        mMainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        mMainViewPagerAdapter.addFragment(RequestFragment(), "Requests")
        mMainViewPagerAdapter.addFragment(ChatsFragment(), "Chats")
        mMainViewPagerAdapter.addFragment(FriendsFragment(), "Friends")

        mViewPager.adapter = mMainViewPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)
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
            R.id.account_settings_menu -> sendToSettingsActivity()

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

    private fun getAllUsers() {
        startActivity(Intent(this@MainActivity, UsersActivity::class.java))
    }

    private fun sendToSettingsActivity() {
        val intent = Intent( this@MainActivity, SettingsActivity::class.java)
        startActivity(intent)
    }
}
