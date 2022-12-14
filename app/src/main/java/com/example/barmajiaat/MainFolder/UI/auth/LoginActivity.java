package com.example.barmajiaat.MainFolder.UI.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.barmajiaat.MainFolder.UI.MainActivity;
import com.example.barmajiaat.MainFolder.UI.data.model.UserModel;
import com.example.barmajiaat.MainFolder.app.Constant;
import com.example.barmajiaat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText username, email, password;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//this code is for hiding the action bar related only to the splash activity [Remove the Slashes before the two following codes to activate it]
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);



        email = findViewById(R.id.login_etEmail);
        password = findViewById(R.id.login_etPassword);
        progressBar = findViewById(R.id.pb_login);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


// this line means, that after you press on the Register Button, go to the validation method, which of course validates if the written stuff are correct.
        findViewById(R.id.btLogin).setOnClickListener(v -> validation());


    }


    private void validation() {


        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();



// this code checks if the userEmail EditText is empty or not.
        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;

        }

// this code checks if the email is written correctly according to a pre made Class called Patterns, so the "!" before the Patterns, means that: if the Patterns of the written email wasn't written according to the roles defined by this class, then make a toast about that.
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return;

        }

// this code checks if the userPassword EditText is empty or not.
        if (userPassword.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;

        }


        if (userPassword.length() < 8) {
            Toast.makeText(this, "The password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;

        }


// this line means, that after you have validated everything, and no problem found, then create an account.
        login(userEmail, userPassword);
    }


    private void login( String userEmail, String userPassword) {

// this line means, that when the validation is complete,and while trying to connect to the firebase, show a loading circle or the ProgressBar in other words.
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {



                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();



                        } else {

                            Toast.makeText(LoginActivity.this, "Unknown Error \n " + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();


// make the progressBar disappear after an error or successful account creation.
                            progressBar.setVisibility(View.GONE);

                        }


                    }
                });


    }}
