package com.jannat.cosmetica.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jannat.cosmetica.Model.ProductModel;
import com.jannat.cosmetica.R;

// The Activity is Launched When a Product is Clicked From Inside of a Category
public class SingleProduct extends AppCompatActivity {
    ImageView product_image;
    Button add_cart;
    TextView product_price, product_title,product_color,product_description;

    // Firebase Initializers
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        Intent i = getIntent();
        String productId = i.getStringExtra("productId");
        String category = i.getStringExtra("category");
        ProductModel pm = (ProductModel) i.getSerializableExtra("product_details");

        //Custom Action Bar Design
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_action, null);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_24_actiono);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  Spinner Size
        Spinner product_size = findViewById(R.id.size_spin);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.sizes_array,
                        android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_size.setAdapter(sizeAdapter);

        // Fetched View from findViewById();
        product_image = findViewById(R.id.product_image);
        product_price = findViewById(R.id.product_price);
        product_title = findViewById(R.id.product_title);
        product_description = findViewById(R.id.product_description);
        product_color = findViewById(R.id.color_val);
        product_price.setText("$ "+ pm.getPrice() + "/-");
        product_title.setText(pm.getTitle());
        product_color.setText(pm.getSize());
        product_description.setText(pm.getDescription());

        // Library used to load an image from the URL specified by "pm.getVisual()" into an ImageView named "product_image".
        Glide.with(getApplicationContext()).load(pm.getVisual()).into(product_image);

        // Cart button functionality
        add_cart = findViewById(R.id.btn_add_cart);
        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();
                String user_id = user.getUid();
                // Firebase Query to save in the cart
                myRef = FirebaseDatabase.getInstance().getReference("users").child(user_id).child("cart");
                myRef.push().setValue(pm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // If Query is Successful Notify User Via Toast
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Added to Cart !!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else{
                            Toast.makeText(getApplicationContext(), "Some Error Occurred Try Again!!", Toast.LENGTH_SHORT).show();
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