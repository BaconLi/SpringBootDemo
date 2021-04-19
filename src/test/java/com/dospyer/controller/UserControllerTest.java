package com.dospyer.controller;

import com.dospyer.domain.User;
import com.dospyer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// 这个代表是Mvc测试，可以传入要测试的Controller类
@WebMvcTest(UserController.class)
public class UserControllerTest {

    // 这个是专门用来测试mvc的类，可以模拟发起http请求
    @Autowired
    private MockMvc mvc;

    // 这里是模拟以一个service组件
    @MockBean
    private UserService userService;

    @Test
    public void testMvc() throws Exception {
        long userId = 2;

        // 这里是模拟service组件的行为
        User user = new User();
        user.setId(userId);
        user.setName("张三");
        user.setAge(20);

        given(this.userService.getUserById(userId)).willReturn(user);

        // 这里是模拟发起一个http请求
        mvc.perform(get("/api/v1.0/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // 返回内容本身检查
                .andExpect(jsonPath("$.name").value("张三"))
                .andExpect(jsonPath("$.age").value("20"));

                //如果返回的 ModelAndView
//                .andExpect(model().attribute("id", 1))
//                .andExpect(model().attribute("name", "张三"))
//                .andExpect(model().attribute("age", 20));
    }

}