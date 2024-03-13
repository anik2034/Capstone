package com.anik.capstone.bookList;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anik.capstone.R;
import com.anik.capstone.bookList.bookListViewModels.BookListViewModel;
import com.anik.capstone.bookList.bookListViewModels.LibraryViewModel;
import com.anik.capstone.bookList.bookListViewModels.RecommendationsViewModel;
import com.anik.capstone.bookList.bookListViewModels.WishlistViewModel;
import com.anik.capstone.bookList.bookWants.BookRecyclerAdapter;
import com.anik.capstone.databinding.FragmentBookListBinding;
import com.anik.capstone.home.DisplayType;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookListFragment extends Fragment {
    private static final String ARG_DISPLAY_TYPE = "ARGS_DISPLAY_TYPE";
    private BookListViewModel bookListViewModel;
    private FragmentBookListBinding fragmentBookListBinding;
    private BookRecyclerAdapter adapter;
    private final int gridColumnCount = 3;

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
        if (bundle != null && bundle.containsKey(ARG_DISPLAY_TYPE)) {
            int displayType = bundle.getInt(ARG_DISPLAY_TYPE);
            int titleResId = 0;
            if (displayType == DisplayType.HOME.ordinal()) {
                titleResId = R.string.home;
                bookListViewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
            } else if (displayType == DisplayType.WISHLIST.ordinal()) {
                titleResId = R.string.wishlist;
                bookListViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
            } else if (displayType == DisplayType.RECOMMENDATIONS.ordinal()) {
                titleResId = R.string.recommendations;
                bookListViewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);
            }
            bookListViewModel.init(titleResId);
        }

        fragmentBookListBinding.setViewModel(bookListViewModel);
        bookListViewModel.loadBooks();

        adapter = new BookRecyclerAdapter();
        adapter.setItemView(ItemViewType.ROW);

        fragmentBookListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentBookListBinding.recyclerView.setAdapter(adapter);

        bookListViewModel.books.observe(getViewLifecycleOwner(), books -> {
            adapter.submitList(books);
        });

        bookListViewModel.itemViewType.observe(getViewLifecycleOwner(), itemViewType -> {
            if (itemViewType == ItemViewType.GRID) {
                switchItemViewType(R.drawable.grid, new LinearLayoutManager(requireContext()), ItemViewType.ROW);
            } else {
                switchItemViewType(R.drawable.row, new GridLayoutManager(requireContext(), gridColumnCount), ItemViewType.GRID);

            }
        });
    }

    private void switchItemViewType(int buttonDrawable, RecyclerView.LayoutManager newLayoutManager, ItemViewType newItemViewType) {
        bookListViewModel.setButtonIcon(buttonDrawable);
        fragmentBookListBinding.recyclerView.setLayoutManager(newLayoutManager);
        fragmentBookListBinding.recyclerView.getRecycledViewPool().clear();
        adapter.setItemView(newItemViewType);
    }
}