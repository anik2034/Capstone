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
    public static final String ARG_ISBN = "ARG_ISBN";
    public static final String ARG_IS_NEW_BOOK = "ARG_IS_NEW_BOOK";
    private FragmentBookDetailsBinding fragmentBookDetailsBinding;

    public static BookDetailsFragment newInstance(String ISBN, boolean isNewBook) {
        Bundle args = new Bundle();
        args.putString(ARG_ISBN, ISBN);
        args.putBoolean(ARG_IS_NEW_BOOK, isNewBook);
        BookDetailsFragment fragment = new BookDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBookDetailsBinding = FragmentBookDetailsBinding.inflate(inflater, container, false);
        return fragmentBookDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBookDetailsBinding.setLifecycleOwner(this);
        BookDetailsViewModel bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARG_ISBN) && bundle.containsKey(ARG_IS_NEW_BOOK)) {
            bookDetailsViewModel.init(bundle.getString(ARG_ISBN), bundle.getBoolean(ARG_IS_NEW_BOOK));
        }
        fragmentBookDetailsBinding.setViewModel(bookDetailsViewModel);
    }
}