package com.anik.capstone.newBook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anik.capstone.R;
import com.anik.capstone.bookDetails.SearchType;
import com.anik.capstone.databinding.FragmentManualInputBinding;
import com.anik.capstone.home.HomeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManualInputFragment extends Fragment {

    private ManualInputViewModel manualInputViewModel;
    private FragmentManualInputBinding fragmentManualInputBinding;

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

        fragmentManualInputBinding.cameraImageButton.setOnClickListener(v ->
                manualInputViewModel.onCameraButtonClicked()
        );

        fragmentManualInputBinding.searchButton.setOnClickListener(v ->
                manualInputViewModel.onSearchButtonClicked(fragmentManualInputBinding.searchEditText.getText().toString())
        );

        fragmentManualInputBinding.isbnRadioButton.setOnClickListener(v -> manualInputViewModel.onSearchTypeClicked(SearchType.ISBN));
        fragmentManualInputBinding.titleRadioButton.setOnClickListener(v -> manualInputViewModel.onSearchTypeClicked(SearchType.TITLE));

        manualInputViewModel.onShowPermissionRequestDialog.observe(getViewLifecycleOwner(), onShowPermissionRequestDialog -> {
            if (onShowPermissionRequestDialog) {
                showPermissionRequestDialog();
            }
        });

        manualInputViewModel.showEmptySearchErrorMessage.observe(getViewLifecycleOwner(), showEmptySearchErrorMessage ->
                Toast.makeText(getContext(), showEmptySearchErrorMessage, Toast.LENGTH_SHORT).show()
        );

        manualInputViewModel.navigateToBarcodeScanner.observe(getViewLifecycleOwner(), navigateToBarcodeScanner -> (
                (HomeActivity) requireActivity()).navigateTo(R.id.barcodeScannerFragment)
        );

        manualInputViewModel.navigateToBookDetails.observe(getViewLifecycleOwner(), bookDetailsData -> (
                (HomeActivity) requireActivity()).navigateTo(
                        R.id.bookDetailsFragment,
                        bookDetailsData.searchValue,
                        bookDetailsData.searchType
                )
        );
    }

    private void showPermissionRequestDialog() {
        Context context = requireContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.permission_dialog_title);
        builder.setMessage(R.string.camera_permission_dialog_body);


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