package com.dospyer.dao;

import com.dospyer.domain.User;

import java.util.List;

public interface UserDAO {

    List<User> listUsers();

    User getUserById(Long userId);

    int saveUser(User user);

    void updateUser(User user);

    void removeUser(Long userId);
}