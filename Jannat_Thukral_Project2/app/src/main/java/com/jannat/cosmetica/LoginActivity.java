package com.jannat.cosmetica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jannat.cosmetica.Screens.AfterLogin;


public class LoginActivity extends AppCompatActivity {
    //Declaring View Types and Variables
    EditText userET, passET;
    Button loginBtn;
    ProgressBar pgB;

    //Firebase Variables
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    //Check on start if user is already Logged in and Send them to main Activity
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // Firebase Prebuilt Functions
        if (firebaseUser != null) { // If Firebase User Null?
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);// Send to Main if True
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);
        //Fetch All Views Using IDs
        //Edit Text Views From Frontend : activity_login.xml
        userET = findViewById(R.id.email);
        passET = findViewById(R.id.password);
        //Fetch Views From Design
        loginBtn = findViewById(R.id.login_btn);
        auth = FirebaseAuth.getInstance();

        //Login Button Click Listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the StringType Values from The EditText Views
                String email_text = userET.getText().toString();
                String pass_text = passET.getText().toString();
                // Set ProgressBar On/Off as per processing
                pgB = findViewById(R.id.pgB);
                pgB.setVisibility(View.VISIBLE);

                // Checking if Input is Empty
                if (TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)) {
                    Toast.makeText(LoginActivity.this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
                    // Set ProgressBar On/Off as per processing
                    pgB = findViewById(R.id.pgB);
                    pgB.setVisibility(View.GONE);
                } else {
                    //Login Functionality via firebase inbuilt functions
                    auth.signInWithEmailAndPassword(email_text, pass_text)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Check if Login is Complete
                                    if (task.isSuccessful()) {
                                        // Take to next screen if Login is Successful
                                        Intent i = new Intent(LoginActivity.this, AfterLogin.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        // Set ProgressBar On/Off as per processing
                                        pgB = findViewById(R.id.pgB);
                                        pgB.setVisibility(View.GONE);
                                        //Fire a Toast and Notify if Login Fails
                                        Toast.makeText(LoginActivity.this, "Incorrect Id/Password!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        // Register View Click Button
        TextView register_view = findViewById(R.id.register_view);
        register_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the Registration Form
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}