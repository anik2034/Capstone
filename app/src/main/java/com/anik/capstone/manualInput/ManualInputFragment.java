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
import com.anik.capstone.addNewBook.BarcodeScannerFragment;
import com.anik.capstone.bookDetails.BookDetailsFragment;
import com.anik.capstone.databinding.FragmentManualInputBinding;
import com.anik.capstone.home.HomeActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManualInputFragment extends Fragment {
    private ManualInputViewModel manualInputViewModel;
    private FragmentManualInputBinding fragmentManualInputBinding;

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
        setUpSpinner();

        fragmentManualInputBinding.cameraImageButton.setOnClickListener(v -> manualInputViewModel.onCameraButtonClicked()
        );

        manualInputViewModel.onShowPermissionRequestDialog.observe(getViewLifecycleOwner(), onShowPermissionRequestDialog -> {
            if (onShowPermissionRequestDialog) {
                showPermissionRequestDialog();
            }
        });

        manualInputViewModel.nextScreen.observe(getViewLifecycleOwner(), nextScreenData -> {
            Fragment fragment = null;
            switch (nextScreenData.getNextScreen()) {
                case BARCODE_SCANNER: {
                    fragment = BarcodeScannerFragment.newInstance();
                    break;
                }
                case BOOK_DETAILS: {
                    fragment = BookDetailsFragment.newInstance(nextScreenData.getData());
                    break;
                }
            }
            ((HomeActivity) requireActivity()).replaceFragment(fragment);
        });
        fragmentManualInputBinding.searchButton.setOnClickListener(v -> manualInputViewModel.onSearchButtonClicked(fragmentManualInputBinding.searchEditText.getText().toString()));
    }

    private void setUpSpinner() {
        Spinner searchOption = fragmentManualInputBinding.searchBySpinner;
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