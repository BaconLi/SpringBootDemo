package com.dospyer.controller;

import com.dospyer.domain.User;
import com.dospyer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// 这里直接在类级别给一个/user的映射，就是代表用户管理的请求都会到这里来
/**
 * （1）系统启动的时候，druid-spring-boot-starter执行，初始化出来一个druid连接池的DataSource bean
 * （2）mybatis-spring-boot-starter接着开始干活儿，发现了一个DataSource bean，就会将其注入SqlSessionFactory，再创建SqlSessionTemplate，接着扫描Mapper接口，将SqlSessionTemplate注入每个Mapper，然后将Mapper放入spring容器中来管理
 * （3）spring boot的@ComponantScan注解开始干活儿，自动扫描所有的bean，依次初始化实例以及注入依赖，EmployeeServiceImpl（将EmployeeMapper注入其中），EmployeeCongtroller（将EmployeeServiceImpl注入其中）
 * （4）此时浏览器发送请求，会先由controlller处理，接着调用service，再调用mapper。mapper底层会基于druid连接池访问到数据库，进行数据操作
 */
@RequestMapping("api/v1.0/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET请求代表着是查询数据
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> listUsers() {
        return userService.listUsers();
    }

    // GET请求+{id}代表的是查询某个用户
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    // POST请求代表着是新增数据
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String saveUser(@Validated({User.Add.class}) User user) {
        userService.saveUser(user);
        return "success";
    }

    // PUT请求代表的是更新
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable("userId") Long userId, User user) {
        user.setId(userId);
        userService.updateUser(user);
        return "success";
    }

    // DELETE请求代表的是删除
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.removeUser(userId);
        return "success";
    }

}