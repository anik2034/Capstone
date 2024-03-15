package com.anik.capstone.bookList.bookWants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.anik.capstone.databinding.FragmentBookWantsBinding;
import com.google.android.material.tabs.TabLayout;


public class BookWantsFragment extends Fragment {
    private FragmentBookWantsBinding fragmentBookWantsBinding;

    public static BookWantsFragment newInstance() {
        return new BookWantsFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        fragmentBookWantsBinding = FragmentBookWantsBinding.inflate(inflater, container, false);
        return fragmentBookWantsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager = fragmentBookWantsBinding.viewPager;
        TabLayout tabLayout = fragmentBookWantsBinding.tabLayout;
        fragmentBookWantsBinding.setLifecycleOwner(this);
        viewPager.setAdapter(new BookWantsViewPagerAdapter(this));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}