package com.anik.capstone.bookList;

import static dagger.hilt.android.internal.Contexts.getApplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.databinding.FragmentBookListBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookListFragment extends Fragment {
    private static final String ARG_TITLE_RES_ID = "TITLE_RES_ID";
    private FragmentBookListBinding fragmentBookListBinding;

    private BookListViewModel bookListViewModel; //would rather had public but cant inject into private

    public static BookListFragment newInstance(int titleResId) {
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE_RES_ID, titleResId);
        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBookListBinding = FragmentBookListBinding.inflate(inflater, container, false);
        bookListViewModel =  new ViewModelProvider(this).get(BookListViewModel.class);
        fragmentBookListBinding.setViewModel(bookListViewModel);
        fragmentBookListBinding.setLifecycleOwner(this);
        return fragmentBookListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARG_TITLE_RES_ID)) {
            int titleResId = bundle.getInt(ARG_TITLE_RES_ID);
            bookListViewModel.init(titleResId);
        }
    }
}