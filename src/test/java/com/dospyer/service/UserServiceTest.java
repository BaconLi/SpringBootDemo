package com.dospyer.service;

import com.dospyer.domain.User;
import com.dospyer.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    // UserService 依赖了 UserMapper ，需要自己模拟一个 UserMapper 出来
    // @MockBean 基于Mockito框架模拟一个UserMapper，放入 spring 容器中去，然后被 UserService 给引用到
    @MockBean
    private UserMapper userMapper;

    // 这个@Sql注解，在执行测试之前，先执行sql语句，初始化数据
    //@Sql({"user.sql"})
    @Test
    public void testGetUser() {
        Long userId = 2l;
        User user = new User();
        user.setName("张三");
        user.setAge(15);
        user.setId(userId);

        // 定义 userMapper.selectById 方法的所有参数，均返回当前 user 对象
        given(this.userMapper.selectById(anyLong())).willReturn(user);

        User userResult = userService.getUserById(userId);
        Assert.assertEquals(userResult, user);
    }


    @Test
    public void testCount() {
        Long userId = 2l;
        UserService userServiceMock = mock(UserService.class);
        userServiceMock.getUserById(userId);

        // 检查 userService 的 getUserById(1) 是否被调用 1 次
        verify(userServiceMock ,times(1)).getUserById(eq(userId));
    }

    @Test
    public void testOrder() {
        Long userId = 2l;
        User user = new User();
        user.setName("张三");
        user.setAge(15);
        user.setId(userId);

        //检查多个接口被调用的顺序
        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.saveUser(eq(user))).thenReturn(1);
        when(userServiceMock.getUserById(eq(userId))).thenReturn(user);

        int count = userServiceMock.saveUser(user);
        User resultUser = userServiceMock.getUserById(userId);
        // 这里就是在验证，是否按照下面的顺序完成的调用
        InOrder inOrder = inOrder(userServiceMock);
        inOrder.verify(userServiceMock).saveUser(user);
        inOrder.verify(userServiceMock).getUserById(userId);
    }


}