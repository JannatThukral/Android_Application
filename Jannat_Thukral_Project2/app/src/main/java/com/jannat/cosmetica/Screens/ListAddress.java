package com.jannat.cosmetica.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jannat.cosmetica.Adapter.AddressAdapter;
import com.jannat.cosmetica.Model.AddressModel;
import com.jannat.cosmetica.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// The Activity to List all the user Saved Addresses
public class ListAddress extends AppCompatActivity {
    //ViewType and Recycler View variable declarations
    Button add_btn;
    AddressAdapter addressAdapter;
    ArrayList<AddressModel> list;
    ArrayList<String> keyList;
    RecyclerView recyclerView;

    //Firebase Initializing Variables
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Launch the CreateAddress Activity on Button Click
        add_btn = findViewById(R.id.add_address);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAddress.class);
                startActivity(intent);
            }
        });

        // Recycler View Implementation and Variables
        recyclerView = findViewById(R.id.address_rv);
        list = new ArrayList<>();
        keyList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(),list,keyList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userid = firebaseUser.getUid();

        // Query to fetch data from user/addresses
        reference = FirebaseDatabase.getInstance().getReference("users").child(userid).child("address");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String amKey = ds.getKey();
                    AddressModel am = ds.getValue(AddressModel.class);
                    list.add(am);
                    keyList.add(amKey);
                    addressAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        list.clear();
        keyList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}