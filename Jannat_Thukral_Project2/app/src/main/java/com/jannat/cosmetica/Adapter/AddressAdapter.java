package com.jannat.cosmetica.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jannat.cosmetica.Model.AddressModel;
import com.jannat.cosmetica.R;
import com.jannat.cosmetica.Screens.ListAddress;

import java.util.ArrayList;

// The Adapter is Used to Fetch and Set Data to
// Profile->Addresses Fragment
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    //Variable Declarations
    Context context;
    ArrayList<AddressModel> list;
    ArrayList<String> keyList;
    FirebaseUser firebaseUser;

    //Constructor for AddressAdapter Class
    public AddressAdapter(Context context, ArrayList<AddressModel> list, ArrayList<String> keyList) {
        this.context = context;
        this.list = list;
        this.keyList = keyList;
    }

    //Prebuilt Method Implementation Deals with ViewCreation and Layout Inflation Methods
    // The Item to be Used in the recycler view is Provided to Inflater here.
    @NonNull
    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false);
        return new MyViewHolder(v);
    }

    //Binding the Activities with the Item ViewHolders
    // Setting the Values Individually to Each of the view in the Item Layout
    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.MyViewHolder holder, int position) {
        // Get Position of Single Item using get position from the Array List and Access Each Key of JSON
        // Data to set to Each of the Views of Frontend
        AddressModel am  = list.get(position);
        String amKey = keyList.get(position);
        int pos = position;
        holder.address_name.setText(am.getFull_name());
        holder.address_line.setText(am.getAddress());
        holder.address_phone.setText(am.getPhone());
        holder.address_cps.setText(am.getCity()+","+am.getZip()+","+am.getState());
        holder.address_delete.setOnClickListener(new View.OnClickListener() {
            // Firebase Database used to Fetch the User's Saved Addresses
            @Override
            public void onClick(View view) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userid = firebaseUser.getUid();
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users")
                        .child(userid).child("address").child(amKey);
                myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Deleted The Item , Refresh View", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context.getApplicationContext(), ListAddress.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //My View Holder is the Item View Element Getter Method .
    // Here One Item of the Recycler View is Received for Fetching of Data
    // Item is Provided By the OnCreateViewHolder() Method
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView address_name, address_line, address_cps,address_phone; //Text Views in the Item
        Button address_delete; // Button in Item
        public MyViewHolder(@NonNull View itemView) {
            // Implement Search on View Items By ID
            super(itemView);
            address_name = itemView.findViewById(R.id.address_name);
            address_line = itemView.findViewById(R.id.address_line);
            address_cps = itemView.findViewById(R.id.address_city_pin_state);
            address_phone = itemView.findViewById(R.id.address_phone);
            address_delete = itemView.findViewById(R.id.remove_address);
        }
    }
}
