package com.anik.capstone.settings;

import com.anik.capstone.db.UserRepository;
import com.anik.capstone.model.UserModel;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

    private final UserRepository userRepository;

    private final MutableLiveData<UserModel> _userModel = new MutableLiveData<>();
    public LiveData<UserModel> userModel = _userModel;

    @Inject
    public SettingsViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init() {
        _userModel.setValue(userRepository.getUser());
    }
}
