package com.anik.capstone.home;

import android.os.Bundle;

import com.anik.capstone.R;
import com.anik.capstone.addNewBook.AddNewBookFragment;
import com.anik.capstone.bookList.BookListFragment;
import com.anik.capstone.bookList.bookListViewModels.LibraryViewModel;
import com.anik.capstone.bookList.bookListViewModels.WishlistViewModel;
import com.anik.capstone.databinding.ActivityHomeBinding;
import com.anik.capstone.settings.SettingsFragment;
import com.anik.capstone.statistics.StatisticsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    private BookListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.init();

        initViews();

        viewModel.displayType.observe(this, displayType -> {
            switch (displayType) {
                case HOME:
                    fragment = BookListFragment.newInstance(R.string.home_fragment);
                    fragment.setViewModel(new ViewModelProvider(this).get(LibraryViewModel.class));
                    replaceFragment(fragment);
                    break;
                case SETTINGS:
                    replaceFragment(SettingsFragment.newInstance());
                    break;
                case WISHLIST:
                    fragment = BookListFragment.newInstance(R.string.wishlist_fragment);
                    fragment.setViewModel(new ViewModelProvider(this).get(WishlistViewModel.class));
                    replaceFragment(fragment);
                    break;
                case STATISTICS:
                    replaceFragment(StatisticsFragment.newInstance());
                    break;
                case ADD:
                    replaceFragment(AddNewBookFragment.newInstance());
                    break;
            }
        });
    }


    private void initViews() {
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            viewModel.onDisplayTypeChange(item.getItemId());
            return true;
        });
        binding.addNewBook.setOnClickListener(view -> viewModel.onDisplayTypeChange(view.getId()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}