package com.tobidaada.chatapp.ui.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.R.id.users_activity_appbar
import com.tobidaada.chatapp.R.id.users_recycler_view
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val toolbar = users_activity_appbar as Toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "All Users"
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        mRecyclerView = users_recycler_view

    }
}
