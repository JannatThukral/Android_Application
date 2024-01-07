package com.jannat.cosmetica.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jannat.cosmetica.Model.ProductModel;
import com.jannat.cosmetica.R;

import java.util.ArrayList;
import java.util.List;

// Fetch  saved items in the cart
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    // Variable Declarations
    Context context;
    ArrayList<ProductModel> list;
    ArrayList<String> keyList;

    // Class Constructor
    public CartAdapter(Context context, List<ProductModel> list, ArrayList<String> keyList) {
        this.context = context;
        this.keyList = keyList;
        this.list = (ArrayList<ProductModel>) list;
    }

    //Prebuilt Method Implementation Deals with ViewCreation and Layout Inflation Methods
    // The Item to be Used in the recycler view is Provided to Inflater here.
    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_items, parent, false);
        return new MyViewHolder(v);
    }

    //Binding the activities layouts with the viewholders.
    // Setting the values to Each of the view in the Item Layout.
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        // Get Position of Single Item from the Array List and Access Each Key of JSON
        // Data to set to Each of the Views of Frontend
        String listTitle  = list.get(position).getTitle();
        String listPrice  = list.get(position).getPrice();
        String listImage = list.get(position).getVisual();
        Glide.with(context.getApplicationContext()).load(listImage).into(holder.cir_img);
        holder.cir_price.setText("$ " + listPrice+  "/-");
        holder.cir_category.setText(listTitle);
        String docId = keyList.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //My View Holder is the Item View Element Getter Method .
    // Here One Item of the Recycler View is Received for Fetching of Data
    // Item is Provided By the OnCreateViewHolder() Method
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView cir_img;
        TextView cir_category , cir_price;

        public MyViewHolder(@NonNull View itemView) {
            // Implement Search on View Items By ID
            super(itemView);
            cir_price = itemView.findViewById(R.id.product_price);
            cir_category = itemView.findViewById(R.id.product_title);
            cir_img = itemView.findViewById(R.id.product_image);


        }
    }
}
