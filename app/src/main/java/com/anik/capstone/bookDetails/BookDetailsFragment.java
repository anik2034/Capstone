package com.anik.capstone.bookDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anik.capstone.databinding.FragmentBookDetailsBinding;
import com.anik.capstone.model.BookModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookDetailsFragment extends Fragment {
    public static final String ARG_BOOK_MODEL = "ARG_BOOK_MODEl";
    public static final String ARG_SEARCH_DATA = "ARG_SEARCH_DATA";
    public static final String ARG_IS_NEW_BOOK = "ARG_IS_NEW_BOOK";
    private FragmentBookDetailsBinding fragmentBookDetailsBinding;

    public static BookDetailsFragment newInstance(String ISBN, boolean isNewBook, BookModel bookModel) {
        Bundle args = new Bundle();
        args.putString(ARG_BOOK_MODEL, ISBN);
        args.putSerializable(ARG_BOOK_MODEL, bookModel);
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
        if (bundle != null && bundle.containsKey(ARG_SEARCH_DATA) && bundle.containsKey(ARG_IS_NEW_BOOK)) {
            //create book model based on search
            // bookDetailsViewModel.init(bundle.getString(ARG_SEARCH_DATA), bundle.getBoolean(ARG_IS_NEW_BOOK));
        } else if (bundle != null && bundle.containsKey(ARG_BOOK_MODEL) && bundle.containsKey(ARG_IS_NEW_BOOK)) {
            bookDetailsViewModel.init((BookModel) bundle.getSerializable(ARG_BOOK_MODEL), bundle.getBoolean(ARG_IS_NEW_BOOK));
        }
        fragmentBookDetailsBinding.setViewModel(bookDetailsViewModel);


        BookDetailAdapter adapter = new BookDetailAdapter();
        fragmentBookDetailsBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBookDetailsBinding.recyclerView2.setAdapter(adapter);
        bookDetailsViewModel.bookDetailsList.observe(getViewLifecycleOwner(), bookDetailsModels -> {
            adapter.submitList(bookDetailsModels);
        });
    }
}