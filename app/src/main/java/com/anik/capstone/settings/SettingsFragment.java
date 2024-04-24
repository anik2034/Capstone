package com.anik.capstone.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anik.capstone.LoginActivity;
import com.anik.capstone.R;
import com.anik.capstone.databinding.FragmentSettingsBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding fragmentSettingsBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        return fragmentSettingsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentSettingsBinding.logout.setOnClickListener(view1 -> {
            AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener(this::handleSignOutDelete);
        });

        fragmentSettingsBinding.deleteAccount.setOnClickListener(view1 -> AuthUI.getInstance()
                .delete(requireContext())
                .addOnCompleteListener(this::handleSignOutDelete));
    }

    private void handleSignOutDelete(Task<Void> task) {
        if (task.isSuccessful()) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(requireContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }
}