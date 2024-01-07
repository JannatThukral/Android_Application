package com.jannat.cosmetica.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jannat.cosmetica.Model.ProductModel;
import com.jannat.cosmetica.R;
import com.jannat.cosmetica.Screens.SingleProduct;

import java.util.ArrayList;
import java.util.List;

// Used to Fetch the Products to HomeFragment Below the First
// Banner Image These Products are Displayed in a Square Box with Title and Price
public class HomeProductsAdapter extends RecyclerView.Adapter<HomeProductsAdapter.MyViewHolder> {
    // Variable Declaration
    Context context;
    ArrayList<ProductModel> list2;
    ArrayList<String> keyList;

    //Class Constructor
    public HomeProductsAdapter(Context context, List<ProductModel> list2,ArrayList<String> keyList) {
        this.context = context;
        this.keyList = keyList;
        this.list2 = (ArrayList<ProductModel>) list2;
    }


    //Prebuilt Method Implementation Deals with ViewCreation and Layout Inflation Methods
    // The Item to be Used in the recycler view is Provided to Inflater here.
    @NonNull
    @Override
    public HomeProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // The Layout Item used here is square_hor_item.xml
        View v = LayoutInflater.from(context).inflate(R.layout.square_hor_item, parent, false);
        return new MyViewHolder(v);
    }

    //Binding the Activities with the Item ViewHolders
    // Setting the Values Individually to Each of the view in the Item Layout
    @Override
    public void onBindViewHolder(@NonNull HomeProductsAdapter.MyViewHolder holder, int position) {
        // Get Position of Single Item from the Array List and Access Each Key of JSON
        // Data to set to Each of the Views of Frontend
        ProductModel listItem = list2.get(position);
        String listTitle  = list2.get(position).getTitle();
        String listPrice  = list2.get(position).getPrice();
        String listImage = list2.get(position).getVisual();
        String docId = keyList.get(position);
        Glide.with(context.getApplicationContext()).load(listImage).into(holder.cir_img);
        holder.cir_price.setText("$ " + listPrice);
        holder.cir_category.setText(listTitle);

        // Implement OnClick functionality on each of the Product Item
        // So when clicked SingleProduct.java Activity is Launched with
        // Detail of Each Product only
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), SingleProduct.class);
                intent.putExtra("product_details", listItem);
                intent.putExtra("productId",docId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list2.size();
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
            cir_price = itemView.findViewById(R.id.list_product_price);
            cir_category = itemView.findViewById(R.id.list_product_title);
            cir_img = itemView.findViewById(R.id.list_product_image);
        }
    }
}
