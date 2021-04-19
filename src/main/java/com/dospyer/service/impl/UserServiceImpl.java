package com.dospyer.service.impl;

import com.dospyer.dao.UserDAO;
import com.dospyer.domain.User;
import com.dospyer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    public List<User> listUsers() {
        return userDAO.listUsers();
    }

    public User getUserById(Long userId) {
        return userDAO.getUserById(userId);
    }

    @Transactional
    public int saveUser(User user) {
        return userDAO.saveUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void removeUser(Long userId) {
        userDAO.removeUser(userId);
    }
}