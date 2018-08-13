package com.tobidaada.chatapp.ui.auth.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tobidaada.chatapp.R;
import com.tobidaada.chatapp.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailText;
    private EditText mPasswordText;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar mToolbar = findViewById(R.id.login_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");

        mEmailText = findViewById(R.id.login_email_et);
        mPasswordText = findViewById(R.id.login_password_et);
        Button mLoginButton = findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailText.getText().toString().trim();
                String password = mPasswordText.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    mProgressDialog.setTitle("Logging In");
                    mProgressDialog.setMessage("Please Wait While Logging in User");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    try {
                        loginUser(email, password);
                    } catch (Exception exception) {
                        mProgressDialog.dismiss();

                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                        Log.e("ChatApp", "Exception Message: " + exception.getMessage());
                    }
                }

            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in Was successful
                            mProgressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            // Error Occurred during Sign in Process
                            mProgressDialog.hide();

                            Toast.makeText(LoginActivity.this,  "Unable to perform Sign in", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}
