package com.jannat.cosmetica.Screens;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jannat.cosmetica.Adapter.ProductListAdapter;
import com.jannat.cosmetica.MainActivity;
import com.jannat.cosmetica.Model.ProductModel;
import com.jannat.cosmetica.databinding.FragmentListProductsBinding;

import java.util.ArrayList;



// Fragment Fetched basically the products from database
// On click of  any category the recycler view will populate the view with the data

public class ListProducts extends Fragment {
    // ViewType and ClassType declarations
    ProductListAdapter productListAdapter;
    ArrayList<ProductModel> list;
    ArrayList<String> keyList;
    RecyclerView recyclerView;

    // Firebase initializing variables
    DatabaseReference reference;
    private FragmentListProductsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = FragmentListProductsBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        // Get the Category Name From the Intent of Previous Screen
        String category = getArguments().getString("category");

        // Recycler View and Adapter
        recyclerView = binding.productList;
        list = new ArrayList<>();
        keyList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(getContext(), list,keyList,category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(productListAdapter);
        // functionality to set grid view of the page in two columns
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        // Firebase Query Structure using Category Name to Get the Products falling Under it
        reference = FirebaseDatabase.getInstance().getReference("products").child(category);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.getKey().equals("imageUrl")){
                        System.out.println("IF");
                    }else{
                        ProductModel pm = ds.getValue(ProductModel.class);
                        list.add(pm);
                        keyList.add(ds.getKey());
                        productListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}