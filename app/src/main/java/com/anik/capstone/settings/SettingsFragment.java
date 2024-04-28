package com.anik.capstone.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anik.capstone.R;
import com.anik.capstone.databinding.FragmentSettingsBinding;
import com.anik.capstone.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding fragmentSettingsBinding;
    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        return fragmentSettingsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        fragmentSettingsBinding.setViewModel(settingsViewModel);

        settingsViewModel.init();

        settingsViewModel.logOut.observe(getViewLifecycleOwner(), aVoid ->
                AuthUI.getInstance()
                        .signOut(requireContext())
                        .addOnCompleteListener(this::handleSignOutDelete)
        );
        settingsViewModel.deleteAccount.observe(getViewLifecycleOwner(), aVoid ->
                AuthUI.getInstance()
                        .delete(requireContext())
                        .addOnCompleteListener(this::handleSignOutDelete)
        );

        fragmentSettingsBinding.logout.setOnClickListener(view1 ->
                settingsViewModel.onLogOutClicked()
        );

        fragmentSettingsBinding.deleteAccount.setOnClickListener(view1 ->
                settingsViewModel.onDeleteAccountClicked()
        );
    }

    private void handleSignOutDelete(Task<Void> task) {
        if (task.isSuccessful()) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        } else {
            Toast.makeText(requireContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }
}