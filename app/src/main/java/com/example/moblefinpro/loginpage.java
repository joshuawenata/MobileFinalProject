package com.example.moblefinpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginpage extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        mAuth = FirebaseAuth.getInstance();

        initialize();
        String emailVal = email.getText().toString();
        String passwordVal = password.getText().toString();

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        mAuth.signInWithEmailAndPassword(emailVal, passwordVal)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful
                            startActivity(new Intent(loginpage.this, MainActivity.class));
                            finish();
                        } else {
                            // Login failed
                            Toast.makeText(loginpage.this, "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initialize() {
        email = findViewById(R.id.loginpage_email);
        password = findViewById(R.id.loginpage_password);
    }
}