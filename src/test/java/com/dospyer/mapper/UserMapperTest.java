package com.dospyer.mapper;

import com.dospyer.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

// @RunWith的意思，是不要使用默认方式进行单元测试，而是使用指定的类来提供单元测试，所有的spring测试都是找SpringRunner.class
@RunWith(SpringRunner.class)
// 这个是spring boot提供的，会一直找到一个Application类，只要包含了@SpringBootApplication的就算，然后会先启动这个类，来给单元测试提供环境
@SpringBootTest
@Transactional
@Rollback(true)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    // 这个@Sql注解，在执行测试之前，先执行sql语句，初始化数据
    //@Sql({"user.sql"})
    public void testAddUser() {
        User user = new User();
        user.setName("李四");
        user.setAge(30);
        userMapper.insert(user);
        Long userId = user.getId();

        Assert.assertTrue(userId > 0);

        // 接着需要从数据库中查询数据来比较
        User resultUser = userMapper.selectById(userId);
        Assert.assertEquals(user, resultUser);
    }

}