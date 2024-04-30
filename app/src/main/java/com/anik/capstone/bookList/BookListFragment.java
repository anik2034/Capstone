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
import com.anik.capstone.home.HomeActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
            } else if (displayType == DisplayType.RECOMMENDATIONS.ordinal()) {
                titleResId = R.string.recommendations;
                bookListViewModel = new ViewModelProvider(this).get(RecommendationsViewModel.class);
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

        bookListViewModel.books.observe(getViewLifecycleOwner(), books -> {
            adapter.submitList(books);
        });

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
}