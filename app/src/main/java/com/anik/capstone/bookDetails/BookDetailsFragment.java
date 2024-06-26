package com.anik.capstone.bookDetails;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.anik.capstone.R;
import com.anik.capstone.databinding.FragmentBookDetailsBinding;
import com.anik.capstone.home.HomeActivity;
import com.anik.capstone.model.ListType;

import java.util.ArrayList;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookDetailsFragment extends Fragment implements BookDetailsAdapter.OnBookDetailItemClickListener {

    public static final String ARG_BOOK_ID = "ARG_BOOK_ID";
    public static final String ARG_SEARCH_TYPE = "ARG_SEARCH_TYPE";
    public static final String ARG_SEARCH_VALUE = "ARG_SEARCH_VALUE";

    private FragmentBookDetailsBinding fragmentBookDetailsBinding;
    private BookDetailsViewModel bookDetailsViewModel;
    private BookDetailsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBookDetailsBinding = FragmentBookDetailsBinding.inflate(inflater, container, false);
        return fragmentBookDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBookDetailsBinding.setLifecycleOwner(this);
        fragmentBookDetailsBinding.setViewModel(bookDetailsViewModel);
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        fragmentBookDetailsBinding.setViewModel(bookDetailsViewModel);
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(ARG_SEARCH_TYPE) && bundle.containsKey(ARG_SEARCH_VALUE)) {
                bookDetailsViewModel.init(
                        SearchType.valueOf(bundle.getString(ARG_SEARCH_TYPE)),
                        bundle.getString(ARG_SEARCH_VALUE), true);
            } else if (bundle.containsKey(ARG_BOOK_ID)) {
                bookDetailsViewModel.init(bundle.getInt(ARG_BOOK_ID), false);
            }
        }

        fragmentBookDetailsBinding.toolbar.inflateMenu(R.menu.toolbar_menu);
        fragmentBookDetailsBinding.toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.save) {
                bookDetailsViewModel.onSaveClicked();
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } else if (itemId == R.id.delete) {
                bookDetailsViewModel.onDeleteClicked();
                back();
            } else if (itemId == R.id.edit) {
                bookDetailsViewModel.onEditClicked();
            }
            return true;
        });

        adapter = new BookDetailsAdapter(this);
        fragmentBookDetailsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBookDetailsBinding.recyclerView.setAdapter(adapter);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(BookDetailsFragment.this)
                        .popBackStack(R.id.bookListFragment, false);
            }
        });

        bookDetailsViewModel.isNewBook.observe(getViewLifecycleOwner(), isVisible -> {
            fragmentBookDetailsBinding.toolbar.getMenu().getItem(0).setVisible(isVisible);
            fragmentBookDetailsBinding.toolbar.getMenu().getItem(1).setVisible(!isVisible);
        });
        bookDetailsViewModel.updateList.observe(getViewLifecycleOwner(), updateList ->
                adapter.notifyDataSetChanged()
        );
        bookDetailsViewModel.onShowBookNotFound.observe(getViewLifecycleOwner(), onShowBookNotFound ->
                showBookNotFoundDialog()
        );
        bookDetailsViewModel.onShowErrorMessage.observe(getViewLifecycleOwner(), errorMessage ->
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show()
        );
        bookDetailsViewModel.bookDetailsList.observe(getViewLifecycleOwner(), bookDetailsModels ->
                adapter.submitList(new ArrayList<>(bookDetailsModels))
        );
        bookDetailsViewModel.updateDetailItem.observe(getViewLifecycleOwner(), position ->
                adapter.notifyItemChanged(position)
        );
        bookDetailsViewModel.onShowChooseListType.observe(getViewLifecycleOwner(), onShowChooseListType ->
                showChooseListType()
        );
        bookDetailsViewModel.updateMenuItemsVisibility.observe(getViewLifecycleOwner(), updateMenuItemsVisibility -> {
            Menu menu = fragmentBookDetailsBinding.toolbar.getMenu();
            menu.findItem(R.id.save).setVisible(updateMenuItemsVisibility.isSaveVisible());
            menu.findItem(R.id.delete).setVisible(updateMenuItemsVisibility.isDeleteVisible());
            menu.findItem(R.id.edit).setVisible(updateMenuItemsVisibility.isEditVisible());
        });
    }

    private void showChooseListType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.choose_book_list_type);
        builder.setMessage(R.string.please_choose_to_where_would_you_like_to_add_this_book);

        builder.setPositiveButton(R.string.my_library, (dialog, which) -> bookDetailsViewModel.onSaveClicked(ListType.LIBRARY));
        builder.setNegativeButton(R.string.wishlist, (dialog, which) -> bookDetailsViewModel.onSaveClicked(ListType.WISHLIST));

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

    }

    private void showBookNotFoundDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.book_not_found);
        builder.setMessage(R.string.sorry_we_couldn_t_find_your_book_would_you_like_to_manually_add_it);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> bookDetailsViewModel.init(-1, true));
        builder.setNegativeButton(R.string.no, (dialog, which) -> {
            dialog.dismiss();
            back();
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onRatingChanged(float rating, BookDetailsItem bookDetailsItem) {
        bookDetailsViewModel.onRatingChanged(rating, bookDetailsItem);
    }

    @Override
    public void onTextChanged(String newText, BookDetailsItem bookDetailsItem) {
        bookDetailsViewModel.onTextChanged(newText, bookDetailsItem);
    }

    @Override
    public void onDateChanged(String date, BookDetailsItem bookDetailsItem) {
        bookDetailsViewModel.onDateChanged(date, bookDetailsItem);
    }

    @Override
    public void onOptionChanged(String selected, BookDetailsItem bookDetailsItem) {
        bookDetailsViewModel.onOptionChanged(selected, bookDetailsItem);
    }

    private void back() {
        ((HomeActivity) requireActivity()).back();
    }
}