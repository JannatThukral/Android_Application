package com.jannat.cosmetica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jannat.cosmetica.Screens.AfterLogin;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //ViewType Declarations
    EditText userET, passET, emailET;
    ImageView imageView;
    Button registerBtn;
    ProgressBar pgB;

    // Firebase Class Variables
    FirebaseAuth auth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Initializing Input Fields from Design: activity_register.xml
        userET = findViewById(R.id.username);
        passET = findViewById(R.id.passwordR);
        emailET = findViewById(R.id.emailR);
        registerBtn = findViewById(R.id.register_btn);

        // Firebase Auth Instance Initialization
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        auth = FirebaseAuth.getInstance();

        // Define functionality when Register Button will be clicked
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set Visibility for Waiting ProgressBar On/OFF
                pgB = findViewById(R.id.pgB);
                pgB.setVisibility(View.VISIBLE);
                // Get the StringType Values from The EditText Views
                String username_text = userET.getText().toString();
                String email_text = emailET.getText().toString();
                String pass_text = passET.getText().toString();
                // Check if Any of the Provided Input is Empty
                if (TextUtils.isEmpty(username_text) || TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)) {
                    // Set Visibility for Waiting ProgressBar On/OFF
                    pgB = findViewById(R.id.pgB);
                    pgB.setVisibility(View.GONE);
                    // Notify if any of the field is empty
                    Toast.makeText(RegisterActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call the registration function
                    RegisterNow(username_text, email_text, pass_text);
                }
            }
        });
    }

    // Registration Function
    private void RegisterNow(final String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Check if the Task is Successful.
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            // Store the user Details after creating the user instance
                            myRef = FirebaseDatabase.getInstance().getReference("users")
                                    .child(userid);
                            // Declare the Data HashMap for Storing the User Values
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            // Opening the Main Activity after Successful Registration
                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Check if Saving of Data is Complete
                                    if (task.isSuccessful()) {
                                        // Take to next screen if Registration is Successful
                                        Intent i = new Intent(RegisterActivity.this, AfterLogin.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        // Set Visibility for Waiting ProgressBar On/OFF
                                        pgB = findViewById(R.id.pgB);
                                        pgB.setVisibility(View.GONE);
                                        //Fire a Toast and Notify if Registration Fails
                                        Toast.makeText(getApplicationContext(), "Failed to create user!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }
}