package com.dospyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dospyer.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: peigen
 * @Date: 2021/4/19 上午9:35
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @Select("SELECT * FROM user")
//    List<User> listUsers();
//
//    @Select("SELECT * FROM user WHERE user_id = #{userId}")
//    User getUserById(@Param("userId") Long userId);
//
//    @Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
//    void saveUser(User user);
//
//    @Update("UPDATE user SET name=#{name}, age=#{age} WHERE user_id=#{userId}")
//    void updateUser(User user);
//
//    @Delete("DELETE FROM user WHERE user_id=#{userId}")
//    void removeUser(@Param("userId") Long userId);
}
