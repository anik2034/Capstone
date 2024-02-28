package com.anik.capstone.addNewBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddNewBookFragment extends Fragment {

    private AddNewBookViewModel addNewBookViewModel;

    public static AddNewBookFragment newInstance() {
        return new AddNewBookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addNewBookViewModel = new ViewModelProvider(this).get(AddNewBookViewModel.class);
        return inflater.inflate(R.layout.fragment_add_new_book, container, false);
    }
}