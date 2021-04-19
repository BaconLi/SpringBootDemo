package com.dospyer.service;

import com.dospyer.domain.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();

    User getUserById(Long userId);

    int saveUser(User user);

    void updateUser(User user);

    void removeUser(Long userId);

}