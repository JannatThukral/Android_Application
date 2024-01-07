package com.jannat.cosmetica.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jannat.cosmetica.R;
import com.jannat.cosmetica.Screens.ListProducts;
import com.jannat.cosmetica.databinding.FragmentDashboardBinding;

// Bottom Navigation , Special Offers Linked Fragment
// View File @layouts/fragment_dashboard.xml
public class DashboardFragment extends Fragment {
    ImageView frame_1 , frame_2 , frame_3 ;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Below is The Banners for Offers and Intents Linked in
        // Form of Fragment Transactions to Link Categories with the Offers
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        frame_1 = binding.frame1;
        frame_2 = binding.frame2;
        frame_3= binding.frame3;
        frame_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListProducts listProducts = new ListProducts();
                Bundle bundle = new Bundle();
                bundle.putString("category","shirts");
                listProducts.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.homePage,listProducts).commit();
            }
        });
        frame_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListProducts listProducts = new ListProducts();
                Bundle bundle = new Bundle();
                bundle.putString("category","tshirts");
                listProducts.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.homePage,listProducts).commit();
            }
        });
        frame_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListProducts listProducts = new ListProducts();
                Bundle bundle = new Bundle();
                bundle.putString("category","jeans");
                listProducts.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.homePage,listProducts).commit();
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