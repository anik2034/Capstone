package com.anik.capstone.bookList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anik.capstone.bookList.bookListViewModels.BookListViewModel;
import com.anik.capstone.databinding.FragmentBookListBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookListFragment extends Fragment {
    private static final String ARG_TITLE_RES_ID = "TITLE_RES_ID";

    private BookListViewModel bookListViewModel;
    private FragmentBookListBinding fragmentBookListBinding;
    private BookRecyclerAdapter adapter;

    public static BookListFragment newInstance(int titleResId) {
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE_RES_ID, titleResId);
        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setViewModel(BookListViewModel viewModel) {
        bookListViewModel = viewModel;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBookListBinding = FragmentBookListBinding.inflate(inflater, container, false);
        return fragmentBookListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentBookListBinding.setViewModel(bookListViewModel);
        fragmentBookListBinding.setLifecycleOwner(this);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARG_TITLE_RES_ID)) {
            int titleResId = bundle.getInt(ARG_TITLE_RES_ID);
            bookListViewModel.init(titleResId);
        }
        bookListViewModel.loadBooks();

        adapter = new BookRecyclerAdapter();
        fragmentBookListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentBookListBinding.recyclerView.setAdapter(adapter);

        bookListViewModel.books.observe(getViewLifecycleOwner(), books -> adapter.setBooks(books));

    }
}