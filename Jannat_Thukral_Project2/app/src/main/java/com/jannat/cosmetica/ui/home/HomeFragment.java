package com.jannat.cosmetica.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jannat.cosmetica.Adapter.CategoryAdapter;
import com.jannat.cosmetica.Adapter.HomeProductsAdapter;
import com.jannat.cosmetica.Model.CategoryModel;
import com.jannat.cosmetica.Model.ProductModel;
import com.jannat.cosmetica.databinding.FragmentHomeBinding;

import java.util.ArrayList;



public class HomeFragment extends Fragment {

    // View Type Declarations to Variables
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> list;
    RecyclerView recyclerView;
    DatabaseReference reference;
    HomeProductsAdapter productListAdapter;
    ArrayList<ProductModel> list2;
    ArrayList<String> keyList;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       //Setting the Recycler View for the page and also setting layout manager to horizontal layout.
        recyclerView = binding.circularCategoryRv;
        list = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Firebase database and Executing Creation of  the Circular Category Views
        //DataChange method of the ValueEventListener. It is called when the data of the node changes.
        reference = FirebaseDatabase.getInstance().getReference("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    reference = FirebaseDatabase.getInstance().getReference("products").child(key).child("imageUrl");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CategoryModel cm = new CategoryModel();
                            cm.setImageUrl(String.valueOf(snapshot.getValue()));
                            cm.setTitle(key);
                            list.add(cm);
                            categoryAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        //Generating the Square Product List
        // Below the Banner Number 1
        recyclerView = binding.squareHor;
        list2 = new ArrayList<>();
        keyList = new ArrayList<>();
        productListAdapter = new HomeProductsAdapter(getContext(), list2,keyList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(productListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        reference = FirebaseDatabase.getInstance().getReference("home_products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                        ProductModel pm = ds.getValue(ProductModel.class);
                        list2.add(pm);
                        keyList.add(ds.getKey());
                        productListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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