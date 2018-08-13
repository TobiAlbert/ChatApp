package com.tobidaada.chatapp.ui.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tobidaada.chatapp.R;
import com.tobidaada.chatapp.ui.main.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText mDisplayNameEdit;
    private TextInputEditText mEmailEdit;
    private TextInputEditText mPasswordEdit;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mDisplayNameEdit = findViewById(R.id.register_name_et);
        mEmailEdit = findViewById(R.id.regsiter_email_et);
        mPasswordEdit = findViewById(R.id.register_password_et);
        mRegisterButton = findViewById(R.id.register_btn);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String displayName = mDisplayNameEdit.getText().toString().trim();
                String email = mEmailEdit.getText().toString().trim();
                String password = mPasswordEdit.getText().toString().trim();

                registerUser(displayName, email, password);

            }
        });

    }

    private void registerUser(String displayName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user
                            Log.i("ChatApp", "Task Result: " + task.getResult().toString());
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}
