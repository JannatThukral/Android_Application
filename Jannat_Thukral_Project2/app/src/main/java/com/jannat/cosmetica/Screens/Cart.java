package com.jannat.cosmetica.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jannat.cosmetica.Adapter.CartAdapter;
import com.jannat.cosmetica.Model.ProductModel;
import com.jannat.cosmetica.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


//The Activity shows the items added to the user's
 //cart and implements the Razorpay payment gateway methods to provide checkout facilities

public class Cart extends AppCompatActivity implements PaymentResultListener {

    CartAdapter cartAdapter;
    ArrayList<ProductModel> list;
    ArrayList<String> keyList;
    RecyclerView recyclerView;
    String user_id;
    Button checkout_btn;
    int total= 0;
    TextView cartTotal;

    // Firebase Initializing Variables
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference myRef, orderRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recycler View Lists and Adapters Setup
        cartTotal = findViewById(R.id.cart_total);
        list = new ArrayList<>();
        keyList = new ArrayList<>();
        cartAdapter = new CartAdapter(getApplicationContext(), list,keyList);
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cartAdapter);

        // Setting position as linear layout to the recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));        auth = FirebaseAuth.getInstance();

        // Firebase Data Fetching Via the UserID
        // Stored in Firebase to get user Specific Data
        user = auth.getCurrentUser();
        user_id = user.getUid();
        // To fetch  data from table and collection
        reference = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("cart");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // If Condition to Ignore the category ImageURL Key
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(ds.getKey().equals("imageUrl")){
                            System.out.println("IF");
                        }else{
                            // Fetching of Products from Database
                            ProductModel pm = ds.getValue(ProductModel.class);
                            list.add(pm);
                            total += Integer.parseInt(pm.getPrice());
                            cartTotal.setText(String.valueOf("$"+ total + "/-"));
                            keyList.add(ds.getKey());
                            cartAdapter.notifyDataSetChanged(); // Notify the Adapter As soon as data in List is updated
                        }
                    }}else{
                    // Set Empty Cart Logo if there is no data found.
                    LinearLayout linLay = findViewById(R.id.empty_cart);
                    linLay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Checkout Button On Click Listener
        checkout_btn = findViewById(R.id.checkout_btn);
        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The Cart Amount and user details to feed to the RazorPay API
                int amount = Math.round(Float.parseFloat(String.valueOf(total))*100);
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_QMMQ2RQU21dctC");
                checkout.setImage(R.mipmap.ic_launcher);
                JSONObject object = new JSONObject();
                try {
                    object.put("name", "Cosmetica");
                    object.put("description", "Order Payment");
                    object.put("theme.color", "");
                    object.put("currency", "CAD");
                    object.put("amount", amount);
                    object.put("prefill.email", user.getEmail());
                    checkout.open(Cart.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Function to Trigger on Successful Payment
    @Override
    public void onPaymentSuccess(String s) {
        // Firebase Database for the Specific User
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String user_id = user.getUid();

        // access the Database and Save the Order Details on successful payment
        orderRef= FirebaseDatabase.getInstance().getReference("users").child(user_id).child("orders");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", s);
        hashMap.put("order", "Cosmetica");
        hashMap.put("price",String.valueOf(total));
        // Push the Payment Data to the referred document
        orderRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                // If Saving is Successful , Set Intent to PaymentSuccessful Activity
                if (task.isSuccessful()) {
                    myRef = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("cart");
                    myRef.removeValue();
                    Intent intent = new Intent(Cart.this , PaymentSuccess.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Some Error Occurred Try Again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // If payment is failed or Cancelled notify user of the event via Toast
    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed due to the error : " + s, Toast.LENGTH_SHORT).show();
    }
    // Functionality to Trigger Event when back is Pressed (If Any)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}