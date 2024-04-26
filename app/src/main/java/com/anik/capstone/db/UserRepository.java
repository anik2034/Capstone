package com.anik.capstone.db;

import com.anik.capstone.model.UserModel;

import java.util.concurrent.ExecutionException;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }
    public UserModel getUser() {
        try {
            return userDao.getUser().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public long insertUser(UserModel user) {
        try {
            return userDao.insertUser(user).get();
        } catch (ExecutionException | InterruptedException e) {
            return -1;
        }
    }

    public int deleteUser(UserModel user) {
        try {
            return userDao.deleteUser(user).get();
        } catch (ExecutionException | InterruptedException e) {
            return -1;
        }
    }

}
