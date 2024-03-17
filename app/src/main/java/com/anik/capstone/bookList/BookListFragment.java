package com.anik.capstone.bookList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anik.capstone.R;
import com.anik.capstone.bookList.viewModels.BookListViewModel;
import com.anik.capstone.bookList.viewModels.LibraryViewModel;
import com.anik.capstone.bookList.viewModels.RecommendationsViewModel;
import com.anik.capstone.bookList.viewModels.WishlistViewModel;
import com.anik.capstone.databinding.FragmentBookListBinding;
import com.anik.capstone.home.DisplayType;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookListFragment extends Fragment {
    private static final String ARG_DISPLAY_TYPE = "ARGS_DISPLAY_TYPE";
    private BookListViewModel bookListViewModel;
    private FragmentBookListBinding fragmentBookListBinding;
    private BookRecyclerAdapter adapter;

    private static final int GRID_COLUMN_COUNT = 3;

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
            bookListViewModel.init(titleResId, LayoutViewType.ROW);
        }

        fragmentBookListBinding.setViewModel(bookListViewModel);

        adapter = new BookRecyclerAdapter();

        fragmentBookListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentBookListBinding.recyclerView.setAdapter(adapter);

        bookListViewModel.books.observe(getViewLifecycleOwner(), books -> {
            adapter.submitList(books);
        });

        bookListViewModel.layoutViewType.observe(getViewLifecycleOwner(), layoutViewType -> {
            switch (layoutViewType) {
                case GRID:
                    changeLayout(R.drawable.row, new GridLayoutManager(requireContext(), GRID_COLUMN_COUNT), LayoutViewType.GRID);
                    break;
                case ROW:
                    changeLayout(R.drawable.grid, new LinearLayoutManager(requireContext()), LayoutViewType.ROW);
                    break;
            }
        });
    }

    private void changeLayout(int iconResId, LinearLayoutManager layoutManager, LayoutViewType layoutViewType) {
        bookListViewModel.setButtonIcon(iconResId);
        fragmentBookListBinding.recyclerView.setLayoutManager(layoutManager);
        fragmentBookListBinding.recyclerView.getRecycledViewPool().clear();
        adapter.setLayoutViewType(layoutViewType);
    }
}