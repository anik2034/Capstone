package com.anik.capstone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
    private NavBarViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NavBarViewModel.class);
        viewModel.init();

		initViews();

        viewModel.displayType.observe(this, displayType -> {
            switch (displayType) {
                case HOME:
                    replaceFragment(BookListFragment.newInstance(R.string.home_fragment));
                    break;
                case SETTINGS:
                    replaceFragment(SettingsFragment.newInstance());
                    break;
                case WISHLIST:
                    replaceFragment(BookListFragment.newInstance(R.string.wishlist_fragment));
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