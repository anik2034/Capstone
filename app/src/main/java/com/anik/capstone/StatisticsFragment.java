package com.anik.capstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StatisticsFragment extends Fragment {
    private StatisticsViewModel statisticsViewModel;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        statisticsViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }
}