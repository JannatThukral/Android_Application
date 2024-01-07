package com.jannat.cosmetica.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.jannat.cosmetica.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

// Activity to Control the Address Input Form
// View File Located @layout/activity_create_address.xml
public class CreateAddress extends AppCompatActivity {
    // Setting the ViewTypes and Variables for the Frontend Forms
    EditText full_name, address, city_town, pin_code, phone_no, email;
    String full_name_val, address_val, city_town_val, pin_code_val, phone_no_val, email_val, country_val, state_val;
    Spinner country, state; // The dropdown selectors of Values (For Eg: Country Prefixes)
    String user_id;
    Button save_continue;
    ProgressBar pgB;

    //Firebase Class Initializers
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Creating the Views with Static Data and Populating Values In It

        //Country Name Spinners
        Spinner country_name = findViewById(R.id.country_spin);
        ArrayAdapter<CharSequence> routeNameAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.country_array,
                        android.R.layout.simple_spinner_item);
        routeNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_name.setAdapter(routeNameAdapter);

        // State Name Spinner
        Spinner state_name = findViewById(R.id.state_spin);
        ArrayAdapter<CharSequence> stateNameAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.state_array,
                        android.R.layout.simple_spinner_item);
        stateNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_name.setAdapter(stateNameAdapter);

        // Fetching Views By Id from layouts
        full_name = findViewById(R.id.inp_full_name);
        address = findViewById(R.id.inp_address);
        city_town = findViewById(R.id.inp_city_town);
        pin_code = findViewById(R.id.inp_zip);
        phone_no = findViewById(R.id.inp_phone);
        email = findViewById(R.id.inp_add_email);
        country = findViewById(R.id.country_spin);
        state = findViewById(R.id.state_spin);
        pgB = findViewById(R.id.pgB);
        save_continue = findViewById(R.id.addSave);

        // Save Button OnClick Functionality
        save_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // progressBar visibility to user experience
                pgB.setVisibility(View.VISIBLE);

                // Getting Strings from Input Controllers
                full_name_val = full_name.getText().toString();
                address_val = address.getText().toString();
                city_town_val = city_town.getText().toString();
                pin_code_val = pin_code.getText().toString();
                phone_no_val = phone_no.getText().toString();
                email_val = email.getText().toString();
                country_val = country.getSelectedItem().toString();
                state_val = state.getSelectedItem().toString();

                // Checking if any of the input is Empty
                if (TextUtils.isEmpty(full_name_val) || TextUtils.isEmpty(address_val) ||
                        TextUtils.isEmpty(city_town_val) || TextUtils.isEmpty(pin_code_val) ||
                        TextUtils.isEmpty(phone_no_val) || TextUtils.isEmpty(email_val) ||
                        TextUtils.isEmpty(country_val) || TextUtils.isEmpty(state_val)) {
                    pgB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                    // Phone Number Validation
                } else if(phone_no_val.length() < 10){
                    pgB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please Enter a Valid Phone No", Toast.LENGTH_SHORT).show();
                    //Pincode Validation
                }else if(pin_code.length()<6){
                    pgB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please Enter a Valid Pin Code", Toast.LENGTH_SHORT).show();
                    //Save Address to Firebase if Everything is Alright
                }else {
                    saveAddress(full_name_val, address_val, city_town_val, pin_code_val, phone_no_val, email_val, country_val, state_val);
                }
            }
        });
    }


    public void saveAddress(final String full_name_val, String address_val, String city_town_val, String pin_code_val, String phone_no_val, String email_val, String country_val, String state_val) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        user_id = user.getUid();
        pgB = findViewById(R.id.pgB);

        myRef = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("address");
        // Creating the object Key-Value Pair to save As it is in Database
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("full_name", full_name_val );
        hashMap.put("address", address_val );
        hashMap.put("city", city_town_val );
        hashMap.put("zip", pin_code_val);
        hashMap.put("phone", phone_no_val);
        hashMap.put("country", country_val);
        hashMap.put("state", state_val);
        hashMap.put("email", email_val );

        //Save Address to User Specific Document Key
        myRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pgB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Saved Address Successfully !!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else{
                    pgB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to save Try Again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}