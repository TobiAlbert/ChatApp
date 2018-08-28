package com.tobidaada.chatapp.ui.status

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.R.id.*
import com.tobidaada.chatapp.utils.*
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mStatusEdit: TextInputEditText
    private lateinit var mUpdateButton: Button
    private lateinit var mProgressBar: ProgressBar

    private lateinit var mStatusDatabase: DatabaseReference
    private lateinit var mCurrentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        mProgressBar = status_progress_bar

        mToolbar = status_appbar as Toolbar
        setSupportActionBar(mToolbar)
        supportActionBar?.title = "Account Status"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        val currentUserId = mCurrentUser.uid

        mStatusDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(currentUserId)

        val intentData = intent
        val status = intentData.getStringExtra("userStatus")

        mStatusEdit = status_input
        mUpdateButton = status_update_button

        mStatusEdit.setText(status)

        mUpdateButton.setOnClickListener { onClickUpdateButton() }
    }

    private fun onClickUpdateButton() {

        mProgressBar.visible()
        mUpdateButton.disable()
        mStatusEdit.disable()

        val status = mStatusEdit.text.toString().trim()

        mStatusDatabase.child("status").setValue(status).addOnCompleteListener {

            task ->
            if (task.isSuccessful) {

            } else {
                this@StatusActivity.toast("There was an error in saving changes")
            }
        }

        mProgressBar.gone()
        mUpdateButton.enable()
        mStatusEdit.enable()
    }
}
