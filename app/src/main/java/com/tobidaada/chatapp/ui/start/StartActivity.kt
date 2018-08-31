package com.tobidaada.chatapp.ui.start

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.tobidaada.chatapp.R
import com.tobidaada.chatapp.ui.auth.login.LoginActivity
import com.tobidaada.chatapp.ui.auth.register.RegisterActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val registerButton = findViewById<Button>(R.id.start_register_btn)
        registerButton.setOnClickListener { startActivity(Intent(this@StartActivity, RegisterActivity::class.java)) }

        val loginButton = findViewById<Button>(R.id.start_login_btn)
        loginButton.setOnClickListener { startActivity(Intent(this@StartActivity, LoginActivity::class.java)) }


    }
}
