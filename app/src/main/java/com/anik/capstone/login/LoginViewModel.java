package com.anik.capstone.login;

import com.anik.capstone.db.UserRepository;
import com.anik.capstone.model.UserModel;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private UserRepository userRepository;
    @Inject
    public LoginViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void onSuccessfulLogin(FirebaseUser currentUser) {
        String id =  currentUser.getUid();
        String name = currentUser.getDisplayName();
        String email = currentUser.getEmail();

        userRepository.insertUser(new UserModel(email,id, name));
    }
}
