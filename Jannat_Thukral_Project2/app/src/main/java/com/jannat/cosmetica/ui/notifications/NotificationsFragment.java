package com.jannat.cosmetica.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jannat.cosmetica.LoginActivity;
import com.jannat.cosmetica.Model.Users;
import com.jannat.cosmetica.Screens.ListAddress;
import com.jannat.cosmetica.Screens.Orders;
import com.jannat.cosmetica.Screens.RaiseTicket;
import com.jannat.cosmetica.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    // Firebase Instance Variable Initialization
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    // Declaring The Input Fields
    TextView email , username;
    private FragmentNotificationsBinding binding;
// To inflate the layout and return the inflated view.
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //TextView from Frontend for Setting Values
        email = binding.profileEmail;
        username = binding.profileUser;

        //Firebase Authentication
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userid = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users")
                .child(userid);

        //Access Database and Get Data
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get UserData from Database
                Users user = dataSnapshot.getValue(Users.class);
                // Set the Fetched Values To the View
                username.setText(user.getUsername());
                email.setText(firebaseUser.getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //Intent for Opening ListAddress Activity
        View create_address = binding.settingsItem1;
        create_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListAddress.class);
                startActivity(intent);
            }
        });

        //Intent for Opening RaiseTicket Activity
        View raise_ticket = binding.settingsItem2;
        raise_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getContext(), RaiseTicket.class);
              startActivity(intent);
            }
        });

        //Intent for Opening Orders Activity
        View my_orders = binding.settingsItem3;
        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Orders.class);
                startActivity(intent);
            }
        });

        // Logout Button Functionality
        // Created using FirebaseAuth
        // signOut() function
        final Button logout_btn;
        logout_btn = binding.logoutBtn;
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}