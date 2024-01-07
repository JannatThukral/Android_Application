package com.jannat.cosmetica.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jannat.cosmetica.Model.CategoryModel;
import com.jannat.cosmetica.R;
import com.jannat.cosmetica.Screens.ListProducts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Used to Fetch the Categories on the Top of the HomeFragment
// and Set the Circular ImageHolding Categories to the Home
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    // Variable Declarations
    Context context;
    ArrayList<CategoryModel> list;

    //Class Constructor
    public CategoryAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = (ArrayList<CategoryModel>) list;
    }

    //Prebuilt Method Implementation Deals with ViewCreation and Layout Inflation Methods
    // The Item to be Used in the recycler view is Provided to Inflater here.
    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Layout used is circular_hor_item.xml
        View v = LayoutInflater.from(context).inflate(R.layout.circular_hor_item, parent, false);
        return new MyViewHolder(v);
    }

    //Binding the Activities with the Item ViewHolders
    // Setting the Values Individually to Each of the view in the Item Layout
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        // Get Position of Single Item from the Array List and Access Each Key of JSON
        // Data to set to Each of the Views of Frontend
        String itemTitle = list.get(position).getTitle();
        String itemImage = list.get(position).getImageUrl();
        holder.cir_category.setText(itemTitle);
        Glide.with(context.getApplicationContext()).load(itemImage).into(holder.cir_img);

        // Send the Category name on click to the
        // ListProducts.java Activity so Data from Category
        // can be gathered and Views Further can be generated
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListProducts listProducts = new ListProducts();
                Bundle bundle = new Bundle();
                bundle.putString("category",itemTitle.toLowerCase(Locale.ROOT));
                listProducts.setArguments(bundle);
                // Intent for Fragment based activities called Transactions in Stack
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.homePage,listProducts).commit();
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
        ImageView cir_img;
        TextView cir_category;
        public MyViewHolder(@NonNull View itemView) {
            // Implement Search on View Items By ID
            super(itemView);
            cir_category = itemView.findViewById(R.id.cir_cate_label);
            cir_img = itemView.findViewById(R.id.cir_cate_img);
        }
    }
}
