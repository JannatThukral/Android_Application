package com.jannat.cosmetica.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jannat.cosmetica.MainActivity;
import com.jannat.cosmetica.R;

// PaymentSuccess activity is a Placeholder Activity
// Launched by onPaymentSuccess() Method of Cart Activity
// View Located in @layout/activity_payment_success.xml
public class PaymentSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intents for My Orders and Shopping Buttons
        setContentView(R.layout.activity_payment_success);
        TextView myOrders = findViewById(R.id.success_btn_orders);
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentSuccess.this , Orders.class);
                startActivity(intent);
            }
        });
        TextView myHome = findViewById(R.id.success_btn_shop);
        myHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentSuccess.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }
}