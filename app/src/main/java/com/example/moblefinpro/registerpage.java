package com.example.moblefinpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerpage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText username, email, password, confirmpassword;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        initialize();
    }

    private void initialize() {
        username = findViewById(R.id.registerpage_username);
        email = findViewById(R.id.registerpage_email);
        password = findViewById(R.id.registerpage_password);
        confirmpassword = findViewById(R.id.registerpage_repassword);
    }

    public void signUpEmail(View view) {
        boolean flag = true;
        String usernameVal = username.getText().toString();
        String emailVal = email.getText().toString();
        String passwordVal = password.getText().toString();
        String confirmPasswordVal = confirmpassword.getText().toString();

        if(usernameVal.isEmpty()){
            Toast.makeText(registerpage.this, "username need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if(emailVal.isEmpty()){
            Toast.makeText(registerpage.this, "email need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if(passwordVal.isEmpty()){
            Toast.makeText(registerpage.this, "password need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if(confirmPasswordVal.isEmpty()){
            Toast.makeText(registerpage.this, "confirm password need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if(!confirmPasswordVal.equals(passwordVal)){
            Toast.makeText(registerpage.this, "password and confirm password need to be match!", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if(flag){
            mAuth.createUserWithEmailAndPassword(emailVal, passwordVal)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // User registration successful
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            // You can perform further actions, such as updating user profile or navigating to the next screen
                            if(firebaseUser != null){
                                String userId = firebaseUser.getUid();
                                DatabaseReference myRef = database.getReference().child("Users").child(userId);
                                myRef.child("userId").setValue(userId);
                                myRef.child("username").setValue(usernameVal);
                                myRef.child("email").setValue(firebaseUser.getEmail());
                            }
                            startActivity(new Intent(registerpage.this, loginpage.class));
                            finish();
                        } else {
                            // User registration failed
                            String errorMessage = task.getException().getMessage();
                            // Handle the error appropriately
                            Toast.makeText(registerpage.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}