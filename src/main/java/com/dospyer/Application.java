package com.dospyer;

import com.dospyer.config.DruidDBConfig;
import com.dospyer.listener.MyApplicationStartedEventListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Author: peigen
 * @Date: 2021/4/19 上午8:38
 */


/**
 *
 * @Configuration
 * spring boot的一个核心思想，就是尽量消除掉各种复杂的xml配置文件，所以一般建议如果要进行一些配置，可以采用Configuration类，在Java类中做一些配置
 * 而且通常比较好的实践是，就将用来放main方法的Application类，作为Configuration类，所以一般会给Application类加@Configuration注解
 * 如果不想将所有的配置都放在Application类中，也可以使用@Import注解将其他的Configuration类引入进来，或者是依靠@ComponentScan注解自动扫描其他的Configuration类
 * 即使一定要使用一个xml配置文件，建议是用@ImportResource来导入一个xml配置文件
 *
 * @EnableAutoConfiguration
 * auto configuration，是spring boot最最重要和核心的功能之一
 * spring boot的核心思想，就是不要去做太多的xml配置，全部基于约定的一些规则，自动完成一些配置
 * auto configuration，就会根据我们引入的一些依赖，比如引入spring-boot-starter-web，就会根据我们要开发web程序的特点，自动完成tomcat服务器等相关的web配置
 *
 * @ComponentScan
 * spring boot可以无缝与spring框架进行整合使用，一般就是在Application类上加@ComponentScan注解，启用自动扫描所有的spring bean，同时搭配好@Autowired注解来进行自动装配。
 *
 * @SpringBootApplication相当于
 *
 * @Configuration
 * @EnableAutoConfiguration
 * @ComponentScan 这三个注解
 */
@SpringBootApplication
// 使用@Import就可以将其他的配置管理类导入进来，使用 druid 的 start 包，注释此方式。也可以基于compontScan自动扫描
//@Import(DruidDBConfig.class)
public class Application {
    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new MyApplicationStartedEventListener());
        app.run(args);
    }
}
