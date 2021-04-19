package com.dospyer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: peigen
 * @Date: 2021/4/19 上午8:53
 */


/**
 * spring boot为spring mvc做的auto configuration
 * （1）自动注册了ContentNegotiatingViewResolver和BeanNameViewResolver两个bean
 * （2）支持处理静态资源
 * （3）自动注册了Converter、GenericConverter、Formatter几个bean
 * （4）支持HttpMessageConverters
 * （5）自动注册了MessageCodesResolver
 * （6）静态index.html支持
 * （7）自定义Favicon图标支持
 * （8）自动使用了ConfigurableWebBindingInitializer bean
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello/{name}")
    public String hello(@PathVariable("name") String name){
        return "hello " + name;
    }
}
