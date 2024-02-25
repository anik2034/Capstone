package com.anik.capstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.databinding.FragmentBookListBinding;

public class BookListFragment extends Fragment {
    private static final String ARG_TITLE_RES_ID = "TITLE_RES_ID";
	private BookListViewModel bookListViewModel;

    public static BookListFragment newInstance(int titleResId) {
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE_RES_ID, titleResId);
        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bookListViewModel = new ViewModelProvider(this).get(BookListViewModel.class);
	    FragmentBookListBinding fragmentBookListBinding = FragmentBookListBinding.inflate(inflater, container, false);
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