package com.anik.capstone.login;

import androidx.lifecycle.ViewModel;

import com.anik.capstone.db.UserRepository;
import com.anik.capstone.model.UserModel;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private UserRepository userRepository;
    @Inject
    public LoginViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void onSuccessfulLogin(FirebaseUser currentUser) {
        String email = currentUser.getEmail();
        String id =  currentUser.getUid();
        userRepository.insertUser(new UserModel(email,id));
    }
}
