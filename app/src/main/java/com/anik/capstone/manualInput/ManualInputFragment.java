package com.anik.capstone.manualInput;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.R;
import com.anik.capstone.addNewBook.AddNewBookFragment;
import com.anik.capstone.bookDetails.BookDetailsFragment;
import com.anik.capstone.databinding.FragmentManualInputBinding;
import com.anik.capstone.home.DisplayType;
import com.anik.capstone.home.HomeActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManualInputFragment extends Fragment {
    private Spinner searchOption;
    private ManualInputViewModel manualInputViewModel;
    private FragmentManualInputBinding fragmentManualInputBinding;
    private String searchQuery;

    public ManualInputFragment() {
    }

    public static ManualInputFragment newInstance() {
        ManualInputFragment fragment = new ManualInputFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManualInputBinding = FragmentManualInputBinding.inflate(inflater, container, false);
        return fragmentManualInputBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManualInputBinding.setLifecycleOwner(this);
        manualInputViewModel = new ViewModelProvider(this).get(ManualInputViewModel.class);
        manualInputViewModel.init();
        setUpSpinner();

        fragmentManualInputBinding.cameraImageButton.setOnClickListener(v -> {
                    manualInputViewModel.cameraStart.observe(getViewLifecycleOwner(), cameraStart -> {
                        if (cameraStart) {
                            manualInputViewModel.setNextScreen(DisplayType.ADD_NEW_BOOK);
                        } else {
                            showPermissionRequestDialog();
                        }
                    });
                }
        );
        manualInputViewModel.nextScreen.observe(getViewLifecycleOwner(), nextScreen -> {
            switch (nextScreen) {
                case BOOK_DETAILS: {
                    ((HomeActivity) requireActivity()).replaceFragment(BookDetailsFragment.newInstance(searchQuery));
                    break;
                }
                case ADD_NEW_BOOK: {
                    ((HomeActivity) requireActivity()).replaceFragment(AddNewBookFragment.newInstance());
                    break;
                }
            }
        });
        fragmentManualInputBinding.searchButton.setOnClickListener(v -> {
            searchQuery = fragmentManualInputBinding.searchEditText.getText().toString();
            manualInputViewModel.setNextScreen(DisplayType.BOOK_DETAILS);
        });
    }

    private void setUpSpinner() {
        searchOption = fragmentManualInputBinding.searchBySpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.search_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchOption.setAdapter(adapter);

    }

    private void showPermissionRequestDialog() {
        Context context = requireContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.permission_dialog_title);
        builder.setMessage(R.string.permission_dialog_body);


        builder.setPositiveButton(R.string.settings_option, (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        builder.setNegativeButton(R.string.cancel_option, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}