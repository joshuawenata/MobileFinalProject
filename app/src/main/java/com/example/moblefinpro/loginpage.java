package com.example.moblefinpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        mAuth = FirebaseAuth.getInstance();

        initialize();
    }

    private void initialize() {
        email = findViewById(R.id.loginpage_email);
        password = findViewById(R.id.loginpage_password);
    }

    public void signInEmail(View view) {
        boolean flag = true;
        String emailVal = email.getText().toString();
        String passwordVal = password.getText().toString();

        if(emailVal.isEmpty()){
            Toast.makeText(loginpage.this, "email need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if(passwordVal.isEmpty()){
            Toast.makeText(loginpage.this, "password need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }else{
            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
            mAuth.signInWithEmailAndPassword(emailVal, passwordVal)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Login successful
                                startActivity(new Intent(loginpage.this, MainActivity.class));
                                finishAffinity();
                            } else {
                                // Login failed
                                Toast.makeText(loginpage.this, "Login failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}