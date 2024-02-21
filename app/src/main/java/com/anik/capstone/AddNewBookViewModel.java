package com.anik.capstone;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddNewBookViewModel extends ViewModel {
    @Inject
    public AddNewBookViewModel() {
    }
}
