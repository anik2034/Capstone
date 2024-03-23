package com.anik.capstone.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.anik.capstone.R;
import com.anik.capstone.bookDetails.BookDetailsFragment;
import com.anik.capstone.bookList.BookListFragment;
import com.anik.capstone.databinding.ActivityHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setViewModel(viewModel);
        viewModel.init();

        initViews();


        navController = Navigation.findNavController(this, R.id.fragmentContainerView);


        viewModel.displayType.observe(this, displayType -> {
            switch (displayType) {
                case HOME:
                    navigateTo(R.id.bookListFragment, displayType);
                    break;
                case SETTINGS:
                    navigateTo(R.id.settingsFragment);
                    break;
                case BOOK_WANTS:
                    navigateTo(R.id.bookWantsFragment);
                    break;
                case STATISTICS:
                    navigateTo(R.id.statisticsFragment);
                    break;
                case BARCODE_SCANNER:
                    navigateTo(R.id.barcodeScannerFragment);
                    break;
                case MANUAL_INPUT:
                    navigateTo(R.id.manualInputFragment);
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

    public void navigateTo(int fragmentId) {
        navController.navigate(fragmentId);
    }

    public void navigateTo(int fragmentId, DisplayType displayType) {
        Bundle args = new Bundle();
        args.putInt(BookListFragment.ARG_DISPLAY_TYPE, displayType.ordinal());
        navController.navigate(fragmentId, args);
    }

    public void navigateTo(int fragmentId, String data, Boolean isNewBook) {
        Bundle args = new Bundle();
        args.putString(BookDetailsFragment.ARG_ISBN, data);
        args.putBoolean(BookDetailsFragment.ARG_IS_NEW_BOOK, isNewBook);
        navController.navigate(fragmentId, args);
    }


}



