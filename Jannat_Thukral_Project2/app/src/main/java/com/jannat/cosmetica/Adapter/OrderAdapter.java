package com.jannat.cosmetica.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jannat.cosmetica.Model.MyOrder;
import com.jannat.cosmetica.R;

import java.util.ArrayList;
import java.util.List;

// Used to Fetch the Paid Orders in the
// Profile -> My Orders Fragment after Purchase is completed
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    // Variable Declaration
    Context context;
    ArrayList<MyOrder> list;

    //Class Constructor
    public OrderAdapter(Context context, List<MyOrder> list) {
        this.context = context;
        this.list = (ArrayList<MyOrder>) list;
    }

    //Prebuilt Method Implementation Deals with ViewCreation and Layout Inflation Methods
    // The Item to be Used in the recycler view is Provided to Inflater here.
    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Layout used here is orders_item.xml
        View v = LayoutInflater.from(context).inflate(R.layout.orders_item, parent, false);
        return new MyViewHolder(v);
    }

    //Binding the Activities with the Item ViewHolders
    // Setting the Values Individually to Each of the view in the Item Layout
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        // Get Position of Single Item from the Array List and Access Each Key of JSON
        // Data to set to Each of the Views of Frontend
        String listTitle  = list.get(position).getOrder();
        String listPrice  = list.get(position).getPrice();
        String listId = list.get(position).getId();
        holder.cir_price.setText("$" +listPrice+  "/-");
        holder.cir_category.setText(listTitle);
        holder.cir_id.setText(listId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //My View Holder is the Item View Element Getter Method .
    // Here One Item of the Recycler View is Received for Fetching of Data
    // Item is Provided By the OnCreateViewHolder() Method
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cir_category , cir_price, cir_id;
        public MyViewHolder(@NonNull View itemView) {
            // Implement Search on View Items By ID
            super(itemView);
            cir_price = itemView.findViewById(R.id.payment_price);
            cir_category = itemView.findViewById(R.id.payment_name);
            cir_id = itemView.findViewById(R.id.payment_id);

        }
    }
}
