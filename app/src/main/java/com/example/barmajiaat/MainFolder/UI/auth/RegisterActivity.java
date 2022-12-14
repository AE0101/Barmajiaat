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

import com.example.barmajiaat.MainFolder.UI.data.model.UserModel;
import com.example.barmajiaat.MainFolder.app.Constant;
import com.example.barmajiaat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, password;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//this code is for hiding the action bar related only to the splash activity [Remove the Slashes before the two following codes to activate it]
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.register_etUserName);
        email = findViewById(R.id.register_etEmail);
        password = findViewById(R.id.register_etPassword);
        progressBar = findViewById(R.id.pb_register);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


// this line means, that after you press on the Register Button, go to the validation method, which of course validates if the written stuff are correct.
        findViewById(R.id.btRegister).setOnClickListener(v -> validation());


    }


    private void validation() {


        String userName = username.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

// this code checks if the userName EditText is empty or not, if it is empty, then a message will appear that notifies the user that the username is required. and returns the loop from the start.
        if (userName.isEmpty()) {
            Toast.makeText(this, "User Name is required", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;

        }


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
        createUser(userName, userEmail, userPassword);
    }


    private void createUser(String userName, String userEmail, String userPassword) {

// this line means, that when the validation is complete,and while trying to connect to the firebase, show a loading circle or the ProgressBar in other words.
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {



                            saveUserData(userName, userEmail, userPassword);

                        } else {

                            Toast.makeText(RegisterActivity.this, "Unknown Error \n " + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();


// make the progressBar disappear after an error or successful account creation.
                            progressBar.setVisibility(View.GONE);

                        }


                    }
                });


    }


    // this line refers to the Class we created called UserModel in the main directory under the Package called data.model
    private void saveUserData(String userName, String userEmail, String userPassword) {

        UserModel userModel = new UserModel(userName, userEmail, userPassword);

// a constant that as far as I understand, refers to the table in the FireStoreDB that holds the values of the Users Collection, which in turn saves the data coming from the UserModel Class, which comes from the user registered data, hopefully I got it right xD
        db.collection(Constant.usersDB)
                .add(userModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Account is created successfully", Toast.LENGTH_SHORT).show();
                            finish();

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);


                        }else {
                            Toast.makeText(RegisterActivity.this, "Unknown Error \n " + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);




                        }

                    }
                });


    }


}