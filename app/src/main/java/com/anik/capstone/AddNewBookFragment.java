package com.anik.capstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddNewBookFragment extends Fragment {

    private AddNewBookViewModel addNewBookViewModel;

    public static AddNewBookFragment newInstance() {
        return new AddNewBookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_book, container, false);
    }
}