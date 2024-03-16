package com.anik.capstone.bookDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.databinding.FragmentBookDetailsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookDetailsFragment extends Fragment {
    private FragmentBookDetailsBinding fragmentBookDetailsBinding;
    private BookDetailsViewModel bookDetailsViewModel;
    private static final String ARG_ISBN = "ARG_ISBN";

    public static BookDetailsFragment newInstance(String ISBN) {
        Bundle args = new Bundle();
        args.putString(ARG_ISBN, ISBN);
        BookDetailsFragment fragment = new BookDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBookDetailsBinding = FragmentBookDetailsBinding.inflate(inflater, container, false);
        return fragmentBookDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBookDetailsBinding.setLifecycleOwner(this);
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARG_ISBN)) {
            bookDetailsViewModel.init(bundle.getString(ARG_ISBN));
        }
        fragmentBookDetailsBinding.setViewModel(bookDetailsViewModel);

    }
}