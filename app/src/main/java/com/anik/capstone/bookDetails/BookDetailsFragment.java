package com.anik.capstone.bookDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anik.capstone.R;
import com.anik.capstone.databinding.FragmentBookDetailsBinding;
import com.anik.capstone.home.HomeActivity;
import com.anik.capstone.model.BookModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookDetailsFragment extends Fragment implements BookDetailAdapter.OnBookDetailItemClickListener {
    public static final String ARG_BOOK_MODEL = "ARG_BOOK_MODEl";
    public static final String ARG_SEARCH_ISBN = "ARG_SEARCH_ISBN";
    public static final String ARG_SEARCH_TITLE = "ARG_SEARCH_TITLE";
    public static final String ARG_IS_NEW_BOOK = "ARG_IS_NEW_BOOK";
    private FragmentBookDetailsBinding fragmentBookDetailsBinding;
    private BookDetailsViewModel bookDetailsViewModel;
    private BookDetailAdapter adapter;

    public static BookDetailsFragment newInstance(String data, String searchType, boolean isNewBook, BookModel bookModel) {
        Bundle args = new Bundle();
        args.putString(searchType, data);
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
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        Bundle bundle = getArguments();
        fragmentBookDetailsBinding.setViewModel(bookDetailsViewModel);

        if (bundle != null && bundle.containsKey(ARG_SEARCH_ISBN) && bundle.containsKey(ARG_IS_NEW_BOOK)) {
            bookDetailsViewModel.init(bundle.getString(ARG_SEARCH_ISBN), SearchType.SEARCH_BY_ISBN);
        } else if (bundle != null && bundle.containsKey(ARG_SEARCH_TITLE) && bundle.containsKey(ARG_IS_NEW_BOOK)) {
            bookDetailsViewModel.init(bundle.getString(ARG_SEARCH_TITLE), SearchType.SEARCH_BY_TITLE);

        } else if (bundle != null && bundle.containsKey(ARG_BOOK_MODEL) && bundle.containsKey(ARG_IS_NEW_BOOK)) {
            bookDetailsViewModel.init((BookModel) bundle.getSerializable(ARG_BOOK_MODEL), bundle.getBoolean(ARG_IS_NEW_BOOK));
        }

        bookDetailsViewModel.searchedBook.observe(getViewLifecycleOwner(), searchedBook -> {
            bookDetailsViewModel.init(searchedBook, bundle.getBoolean(ARG_IS_NEW_BOOK));
        });
        bookDetailsViewModel.onShowBookNotFound.observe(getViewLifecycleOwner(), onShowBookNotFound -> {
            if (!onShowBookNotFound) showBookNotFoundDialog();
        });

        adapter = new BookDetailAdapter(this);
        fragmentBookDetailsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBookDetailsBinding.recyclerView.setAdapter(adapter);

        bookDetailsViewModel.bookDetailsList.observe(getViewLifecycleOwner(), bookDetailsModels -> {
            adapter.submitList(new ArrayList<>(bookDetailsModels));
        });

        bookDetailsViewModel.updateDetailItem.observe(getViewLifecycleOwner(), position -> {
            adapter.notifyItemChanged(position);
        });
    }

    private void showBookNotFoundDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.book_not_found);
        builder.setMessage(R.string.sorry_we_couldn_t_find_your_book_would_you_like_to_manually_add_it);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> bookDetailsViewModel.init(null, true));
        builder.setNegativeButton(R.string.no, (dialog, which) -> {
            dialog.dismiss();
            ((HomeActivity) getActivity()).back();

        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onItemClick(int position) {
        bookDetailsViewModel.onItemClicked(position);
    }

    @Override
    public void onRatingChanged(float rating, int position) {
        bookDetailsViewModel.onRatingChanged(rating, position);
    }

    @Override
    public void onTextChanged(String newText, int position) {
        bookDetailsViewModel.onTextChanged(newText, position);
    }

    @Override
    public void onDateChanged(String date, int position) {
        bookDetailsViewModel.onDateChanged(date, position);
    }

    @Override
    public void onOptionChanged(String selected, int position) {
        bookDetailsViewModel.onOptionChanged(selected, position);
    }
}