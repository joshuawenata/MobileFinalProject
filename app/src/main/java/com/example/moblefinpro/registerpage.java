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

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    private EditText username, email, password;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        createRequest();
        initialize();
    }

    private void initialize() {
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void signUpGoogle(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if(firebaseUser != null){
                                String userId = firebaseUser.getUid();
                                DatabaseReference myRef = database.getReference().child("Users").push();
                                myRef.child("userId").setValue(userId);
                                myRef.child("username").setValue(firebaseUser.getDisplayName());
                                myRef.child("email").setValue(firebaseUser.getEmail());
                            }
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(registerpage.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void signUpEmail(View view) {
        String emailVal = email.getText().toString();
        String passwordVal = password.getText().toString();
        String usernameVal = username.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailVal, passwordVal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User registration successful
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        // You can perform further actions, such as updating user profile or navigating to the next screen
                        if(firebaseUser != null){
                            String userId = firebaseUser.getUid();
                            DatabaseReference myRef = database.getReference().child("Users").push();
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