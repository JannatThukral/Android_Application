package com.jannat.cosmetica.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jannat.cosmetica.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

// The Activity is Used to Launch a Support Form for The Customer to register Problem.
// Location to Access : Profile=> Raise Ticket
// View File Location : @layouts/activity_raise_ticket
public class RaiseTicket extends AppCompatActivity {
    // ViewType Declaration
    EditText emailEdt , messageEdt;
    Button submit;

    // Firebase Class Initialization
    FirebaseAuth auth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_ticket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emailEdt = findViewById(R.id.title_contact);
        messageEdt = findViewById(R.id.message_contact);
        submit = findViewById(R.id.submit_btn);

        // Initialize Database Variables for Storing Complaints
        auth = FirebaseAuth.getInstance();
        String complaint_email = FirebaseAuth.getInstance().getCurrentUser().getEmail(); // Get Current user's email using prebuilt functions

        //Functionality of onclick submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the EditText Values and Convert them to Strings
                String complaint_msg = messageEdt.getText().toString();
                String complaint_title = emailEdt.getText().toString();
                // Check if the Input Values are Filled Properly or not
                if(TextUtils.isEmpty(complaint_title)||TextUtils.isEmpty(complaint_msg)){
                    Toast.makeText(getApplicationContext(), "Fill all Fields",Toast.LENGTH_SHORT).show();
                }else{
                    // If everything is alright Execute Submission
                    submitForm(complaint_title,complaint_msg);
                }
            };

            // Functionality for Form Submission Procedures
            private void submitForm(final String complaint_title , String complaint_message ){
                // Declare Database
                Date currentTime = Calendar. getInstance(). getTime();
                myRef= FirebaseDatabase.getInstance().getReference("complaint");
                // Declare Hashmap for storing Data
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("timestamp", String.valueOf(currentTime));
                hashMap.put("sender", complaint_email);
                hashMap.put("message", complaint_message);
                hashMap.put("title",complaint_title);

                // Trigger Functionality Once Successful Submission is done
                myRef.child(String.valueOf(UUID.randomUUID())).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // Condition for Successful Submission
                        if (task.isSuccessful()) {
                            // Notify Users and Reset the Form
                            Toast.makeText(getApplicationContext(), "Complaint Registered Successfully!!", Toast.LENGTH_LONG).show();
                            emailEdt.setText(null);
                            messageEdt.setText(null);
                        }else{
                            // Notify for Failure
                            Toast.makeText(getApplicationContext(), "Failed to Register Complaint! Try Again!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}