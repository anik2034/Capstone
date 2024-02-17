package com.example.capstone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstone.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(BookListFragment.newInstance(R.string.home_fragment));
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(BookListFragment.newInstance(R.string.home_fragment));
            } else if (itemId == R.id.statistics) {
                replaceFragment(new StatisticsFragment());
            } else if (itemId == R.id.settings) {
                replaceFragment(new SettingsFragment());
            } else if (itemId == R.id.wishlist) {
                replaceFragment(BookListFragment.newInstance(R.string.wishlist_fragment));
            }
            return true;
        });
        binding.addNewBook.setOnClickListener(view -> replaceFragment(new AddNewBookFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}