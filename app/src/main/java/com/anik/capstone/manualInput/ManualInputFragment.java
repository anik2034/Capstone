package com.anik.capstone.manualInput;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.R;
import com.anik.capstone.bookDetails.BookDetailsFragment;
import com.anik.capstone.databinding.FragmentManualInputBinding;
import com.anik.capstone.home.HomeActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManualInputFragment extends Fragment {
    private ManualInputViewModel manualInputViewModel;
    private FragmentManualInputBinding fragmentManualInputBinding;
    private String searchType;

    public static ManualInputFragment newInstance() {
        return new ManualInputFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManualInputBinding = FragmentManualInputBinding.inflate(inflater, container, false);
        return fragmentManualInputBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManualInputBinding.setLifecycleOwner(this);
        manualInputViewModel = new ViewModelProvider(this).get(ManualInputViewModel.class);
        manualInputViewModel.init();


        fragmentManualInputBinding.cameraImageButton.setOnClickListener(v -> manualInputViewModel.onCameraButtonClicked()
        );

        fragmentManualInputBinding.searchButton.setOnClickListener(v -> {
            if (manualInputViewModel.searchType.getValue() != null) {
                manualInputViewModel.onSearchButtonClicked(fragmentManualInputBinding.searchEditText.getText().toString());
            } else {
                showSearchTypeDialog();
            }


        });

        manualInputViewModel.onShowPermissionRequestDialog.observe(getViewLifecycleOwner(), onShowPermissionRequestDialog -> {
            if (onShowPermissionRequestDialog) {
                showPermissionRequestDialog();
            }
        });

        fragmentManualInputBinding.isbnRadioButton.setOnClickListener(v -> {
            manualInputViewModel.onSearchTypeClicked(BookDetailsFragment.ARG_SEARCH_ISBN);
        });
        fragmentManualInputBinding.titleRadioButton.setOnClickListener(v -> {
            manualInputViewModel.onSearchTypeClicked(BookDetailsFragment.ARG_SEARCH_TITLE);
        });

        manualInputViewModel.searchType.observe(getViewLifecycleOwner(), searchType -> {
            this.searchType = searchType;
        });

        manualInputViewModel.nextScreen.observe(getViewLifecycleOwner(), nextScreenData -> {
            int fragmentId = 0;
            String data = null;
            switch (nextScreenData.getNextScreen()) {
                case BARCODE_SCANNER: {
                    fragmentId = R.id.barcodeScannerFragment;
                    break;
                }
                case BOOK_DETAILS: {
                    fragmentId = R.id.bookDetailsFragment;
                    data = nextScreenData.getData();
                    break;
                }
            }
            ((HomeActivity) requireActivity()).navigateTo(fragmentId, data, true, searchType);
        });

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

    private void showSearchTypeDialog() {
        Context context = requireContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Search Type Required");
        builder.setMessage("Please select the search type");


        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}