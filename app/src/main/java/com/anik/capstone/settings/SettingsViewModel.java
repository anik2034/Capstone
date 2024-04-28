package com.anik.capstone.settings;

import com.anik.capstone.db.BookRepository;
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
    private final BookRepository bookRepository;
    private UserModel user;

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    public LiveData<String> userName = _userName;
    private final MutableLiveData<String> _userEmail = new MutableLiveData<>();
    public LiveData<String> userEmail = _userEmail;

    private final MutableLiveData<Void> _logOut = new MutableLiveData<>();
    public LiveData<Void> logOut = _logOut;

    private final MutableLiveData<Void> _deleteAccount = new MutableLiveData<>();
    public LiveData<Void> deleteAccount = _deleteAccount;

    @Inject
    public SettingsViewModel(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void init() {
        user = userRepository.getUser();
        _userName.setValue(user.getName());
        _userEmail.setValue(user.getEmail());
    }

    public void onLogOutClicked() {
        deleteData(false);
        _logOut.setValue(null);
    }

    public void onDeleteAccountClicked() {
        deleteData(true);
        _deleteAccount.setValue(null);
    }

    private void deleteData(boolean deleteFromCloud) {
        userRepository.deleteUser(user, deleteFromCloud);
        bookRepository.deleteAllBooks(user.getId(), deleteFromCloud);
    }
}
