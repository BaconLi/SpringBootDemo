package com.dospyer.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dospyer.dao.UserDAO;
import com.dospyer.domain.User;
import com.dospyer.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private UserMapper userMapper;

    public List<User> listUsers() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    public void removeUser(Long userId) {
        userMapper.deleteById(userId);
    }
}