package com.tobidaada.chatapp.ui.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.data.datamodel.Users
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_users.*


class UsersActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mUsersDatabase: DatabaseReference
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Users, UsersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val toolbar = users_activity_appbar as Toolbar
        mUsersDatabase = FirebaseDatabase.getInstance().reference.child("Users")

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "All Users"
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        mRecyclerView = users_recycler_view as RecyclerView
        mRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UsersActivity)

        }

    }

    override fun onStart() {
        super.onStart()

        val query = mUsersDatabase.limitToFirst(100)

        val options = FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users::class.java)
                .build()

        mAdapter = object: FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
                val rootView = LayoutInflater.from(this@UsersActivity).inflate(R.layout.custom_user_layout, parent, false)
                return UsersViewHolder(rootView)
            }

            override fun onBindViewHolder(holder: UsersViewHolder, position: Int, model: Users) {
                Glide.with(this@UsersActivity)
                        .load(model.image)
                        .into(holder.mUserImage)

                holder.mUserNameTextView.text = model.name
                holder.mUsersStatusTextView.text = model.status
            }
        }
        mAdapter.startListening()
        mRecyclerView.adapter = mAdapter

    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    inner class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val mUserImage = itemView.findViewById<CircleImageView>(R.id.users_image_view)
        val mUserNameTextView = itemView.findViewById<TextView>(R.id.users_username_tv)
        val mUsersStatusTextView = itemView.findViewById<TextView>(R.id.users_status_tv)

    }
}
