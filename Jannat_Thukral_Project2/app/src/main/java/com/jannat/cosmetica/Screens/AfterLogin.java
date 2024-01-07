package com.jannat.cosmetica.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jannat.cosmetica.MainActivity;
import com.jannat.cosmetica.R;

// This Screen is Shown to the First time User of the App
// After the initial login the screen is displayed with a graphic
// then is removed till next Refresh event
public class AfterLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        getSupportActionBar().hide();
        TextView tv = findViewById(R.id.start_shopping);
        //Simple Intent Event on The Start Shopping Button
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLogin.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }
}