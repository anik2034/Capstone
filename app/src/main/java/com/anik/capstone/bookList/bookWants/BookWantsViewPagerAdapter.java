package com.anik.capstone.bookList.bookWants;

import static com.anik.capstone.home.DisplayType.RECOMMENDATIONS;
import static com.anik.capstone.home.DisplayType.WISHLIST;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.anik.capstone.bookList.BookListFragment;

public class BookWantsViewPagerAdapter extends FragmentStateAdapter {

    public BookWantsViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return BookListFragment.newInstance(WISHLIST);
            case 1:
                return BookListFragment.newInstance(RECOMMENDATIONS);
        }
        throw new IllegalArgumentException("Invalid position: " + position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
