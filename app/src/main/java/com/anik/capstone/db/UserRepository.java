package com.anik.capstone.db;

import com.anik.capstone.model.UserModel;

import java.util.concurrent.ExecutionException;

public class UserRepository {
    private final UserDao userDao;
    private final FirestoreDB firestoreDB;

    public UserRepository(UserDao userDao, FirestoreDB firestoreDB) {
        this.userDao = userDao;
        this.firestoreDB = firestoreDB;
    }

    public UserModel getUser() {
        try {
            return userDao.getUser().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(UserModel user) {
        firestoreDB.addUser(user);
        userDao.insertUser(user);
    }

    public void deleteUser(UserModel user, boolean deleteFromCloud) {
        if (deleteFromCloud) firestoreDB.deleteUser(user);
        userDao.deleteUser(user);
    }

}
