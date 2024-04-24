package com.anik.capstone.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anik.capstone.LoginActivity;
import com.anik.capstone.R;
import com.anik.capstone.databinding.FragmentBookListBinding;
import com.anik.capstone.databinding.FragmentSettingsBinding;
import com.anik.capstone.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding fragmentSettingsBinding;
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        return fragmentSettingsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        fragmentSettingsBinding.logout.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
        });
    }
}