package com.anik.capstone.login;

import com.anik.capstone.db.BookRepository;
import com.anik.capstone.db.UserRepository;
import com.anik.capstone.model.UserModel;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final MutableLiveData<Void> _navigateToHome = new MutableLiveData<>();
    public LiveData<Void> navigateToHome = _navigateToHome;
    private final MutableLiveData<Void> _logout = new MutableLiveData<>();
    public LiveData<Void> logout = _logout;
    private final MutableLiveData<Void> _showErrorMessage = new MutableLiveData<>();
    public LiveData<Void> showErrorMessage = _showErrorMessage;

    @Inject
    public LoginViewModel(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void onSuccessfulLogin(FirebaseUser currentUser) {
        String id = currentUser.getUid();
        String name = currentUser.getDisplayName();
        String email = currentUser.getEmail();

        userRepository.insertUser(new UserModel(email, id, name));
        bookRepository.fetchAndInsertBooks(
                id,
                () -> _navigateToHome.postValue(null),
                () -> {
                    _logout.postValue(null);
                    _showErrorMessage.postValue(null);
                }
        );

    }
}
