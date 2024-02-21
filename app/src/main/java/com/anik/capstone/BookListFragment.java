package com.anik.capstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.databinding.FragmentBookListBinding;

public class BookListFragment extends Fragment {
    private static final String ARG_TITLE_ID = "TITLE_ID";
    private FragmentBookListBinding fragmentBookListBinding;
    private BookListViewModel bookListViewModel;

    public static BookListFragment newInstance(int titleID) {
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE_ID, titleID);
        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bookListViewModel = new ViewModelProvider(this).get(BookListViewModel.class);
        fragmentBookListBinding = FragmentBookListBinding.inflate(inflater, container, false);
        return fragmentBookListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARG_TITLE_ID)) {
            int titleID = bundle.getInt(ARG_TITLE_ID);
            setText(titleID);
        }
    }

    public void setText(int id) {
        if (fragmentBookListBinding != null) {
            fragmentBookListBinding.setTitle(id);
        }
    }
}