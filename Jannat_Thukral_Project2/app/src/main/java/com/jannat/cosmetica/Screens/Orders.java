package com.jannat.cosmetica.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jannat.cosmetica.Adapter.OrderAdapter;
import com.jannat.cosmetica.Model.MyOrder;
import com.jannat.cosmetica.R;

import java.util.ArrayList;

// This Activity is Shown in Profile Section as MyOrders
// This is Used to Fetch the List of Completed Orders from Database
// View Location @layout/activity_orders.xml
public class Orders extends AppCompatActivity {

    // Recycler View and Related Variables ViewTypes
    OrderAdapter orderAdapter;
    ArrayList<MyOrder> list;
    RecyclerView recyclerView;
    String user_id;

    // Firebase Class Initializer Variables
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recycler View Functionality Definition and Adapter Serialization
        recyclerView = findViewById(R.id.order_list);
        list = new ArrayList<>();
        orderAdapter = new OrderAdapter(getApplicationContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        // Firebase Variables Definition
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        user_id = user.getUid();
        // Query Formation FirebaseSDK
        reference = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        MyOrder pm = ds.getValue(MyOrder.class);
                        list.add(pm);
                        orderAdapter.notifyDataSetChanged();
                    }}else{
                    // Show View if No Previous Orders Are Found
                    LinearLayout linLay = findViewById(R.id.empty_orders);
                    linLay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}