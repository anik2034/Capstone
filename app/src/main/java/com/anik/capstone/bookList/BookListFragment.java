package com.anik.capstone.bookList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.anik.capstone.R;
import com.anik.capstone.bookList.viewModels.BookListViewModel;
import com.anik.capstone.bookList.viewModels.LibraryViewModel;
import com.anik.capstone.bookList.viewModels.RecommendationsViewModel;
import com.anik.capstone.bookList.viewModels.WishlistViewModel;
import com.anik.capstone.databinding.FragmentBookListBinding;
import com.anik.capstone.home.DisplayType;
import com.anik.capstone.home.HomeActivity;
import com.anik.capstone.model.ListType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookListFragment extends Fragment implements  BookRecyclerAdapter.OnItemClickListener{

    public static final String ARG_DISPLAY_TYPE = "ARG_DISPLAY_TYPE";
    private static final int GRID_COLUMN_COUNT = 3;

    private BookListViewModel bookListViewModel;
    private FragmentBookListBinding fragmentBookListBinding;
    private BookRecyclerAdapter adapter;

    public static BookListFragment newInstance(DisplayType displayType) {
        Bundle args = new Bundle();
        args.putInt(ARG_DISPLAY_TYPE, displayType.ordinal());
        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBookListBinding = FragmentBookListBinding.inflate(inflater, container, false);
        return fragmentBookListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentBookListBinding.setLifecycleOwner(this);

        Bundle bundle = getArguments();
        int titleResId = 0;
        if (bundle != null && bundle.containsKey(ARG_DISPLAY_TYPE)) {
            int displayType = bundle.getInt(ARG_DISPLAY_TYPE);
            if (displayType == DisplayType.HOME.ordinal()) {
                titleResId = R.string.home;
                bookListViewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
            } else if (displayType == DisplayType.WISHLIST.ordinal()) {
                titleResId = R.string.wishlist;
                bookListViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
                ((WishlistViewModel) bookListViewModel).onShowChooseListType.observe(getViewLifecycleOwner(), onShowChooseListType ->
                        showAddToLibrary());
            } else if (displayType == DisplayType.RECOMMENDATIONS.ordinal()) {
                titleResId = R.string.for_you;
                bookListViewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);
                ((RecommendationsViewModel) bookListViewModel).onShowChooseListType.observe(getViewLifecycleOwner(), onShowChooseListType ->
                        showChooseListType());
            }
        } else {
            titleResId = R.string.home;
            bookListViewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
        }
        bookListViewModel.init(titleResId, LayoutViewType.ROW);

        fragmentBookListBinding.setViewModel(bookListViewModel);

        adapter = new BookRecyclerAdapter(this);

        fragmentBookListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentBookListBinding.recyclerView.setAdapter(adapter);

        bookListViewModel.books.observe(getViewLifecycleOwner(), books -> adapter.submitList(books));

        bookListViewModel.navigateToBookDetails.observe(getViewLifecycleOwner(), bookModelId ->
            ((HomeActivity) requireActivity()).navigateTo(R.id.bookDetailsFragment, bookModelId));

        bookListViewModel.layoutViewType.observe(getViewLifecycleOwner(), layoutViewType -> {
            switch (layoutViewType) {
                case GRID:
                    changeLayout(R.drawable.ic_row, new GridLayoutManager(requireContext(), GRID_COLUMN_COUNT), LayoutViewType.GRID);
                    break;
                case ROW:
                    changeLayout(R.drawable.ic_grid, new LinearLayoutManager(requireContext()), LayoutViewType.ROW);
                    break;
            }
        });


        fragmentBookListBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookListViewModel.searchBooks(newText);
                return true;
            }
        });
    }
    @Override
    public void onItemClick(BookListItem bookListItem) {
        bookListViewModel.onItemClick(bookListItem);
    }

    private void changeLayout(int iconResId, LinearLayoutManager layoutManager, LayoutViewType layoutViewType) {
        bookListViewModel.setButtonIcon(iconResId);
        fragmentBookListBinding.recyclerView.setLayoutManager(layoutManager);
        fragmentBookListBinding.recyclerView.getRecycledViewPool().clear();
        adapter.setLayoutViewType(layoutViewType);
    }

    private void showChooseListType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.choose_book_list_type);
        builder.setMessage(R.string.please_choose_to_where_would_you_like_to_add_this_book);

        builder.setPositiveButton(R.string.my_library, (dialog, which) ->  ((RecommendationsViewModel) bookListViewModel).onSave(ListType.LIBRARY));
        builder.setNegativeButton(R.string.wishlist, (dialog, which) ->  ((RecommendationsViewModel) bookListViewModel).onSave(ListType.WISHLIST));

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

    }

    private void showAddToLibrary() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.add_to_library);
        builder.setMessage(R.string.do_you_want_to_add_this_book_to_the_library);

        builder.setPositiveButton(R.string.yes, (dialog, which) ->  ((WishlistViewModel) bookListViewModel).onSave(ListType.LIBRARY));
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss() );

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

    }
}