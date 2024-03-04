package com.anik.capstone.home;

import static com.anik.capstone.home.DisplayType.HOME;
import static com.anik.capstone.home.DisplayType.WISHLIST;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.R;
import com.anik.capstone.addNewBook.AddNewBookFragment;
import com.anik.capstone.bookList.BookListFragment;
import com.anik.capstone.databinding.ActivityHomeBinding;
import com.anik.capstone.settings.SettingsFragment;
import com.anik.capstone.statistics.StatisticsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setViewModel(viewModel);
        viewModel.init();

        initViews();

        viewModel.displayType.observe(this, displayType -> {
            switch (displayType) {
                case HOME:
                    replaceFragment(BookListFragment.newInstance(HOME));
                    break;
                case SETTINGS:
                    replaceFragment(SettingsFragment.newInstance());
                    break;
                case WISHLIST:
                    replaceFragment(BookListFragment.newInstance(WISHLIST));
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
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            viewModel.onDisplayTypeChange(item.getItemId());
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}