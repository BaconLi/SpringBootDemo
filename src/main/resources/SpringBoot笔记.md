## 基础配置

### 监听端口配置

在application.properties中：server.port=9090
启动系统的时候：java -jar target\actuator-1.0.0.jar --server.port=9090
启动系统的时候：java -Dserver.port=9090 -jar target\actuator-1.0.0.jar

### web上下文配置
默认的web上下文是：/
在application.properties中：server.context-path=/actuator



### 使用其他web服务器

默认的web服务器是用的内嵌的tomcat，可以使用jetty或者是undertow

比如使用jetty作为web服务器

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
   <exclusions>
     <exclusion>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-tomcat</artifactId>
     </exclusion>
   </exclusions>
</dependency>

<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

###  使用tomcat时部分关键配置

```properties
# 打开tomcat访问日志
server.tomcat.accesslog.enabled=true
# 访问日志所在的目录
server.tomcat.accesslog.directory=logs
# 允许HTTP请求缓存到请求队列的最大个数，默认是不限制的
server.tomcat.accept-count=
# 最大连接数，默认是不限制的，如果连接数达到了上限，那么剩下的连接就会保存到请求缓存队列里，也就是上面参数指定的个数
server.tomcat.max-connections=
# 最大工作线程数
server.tomcat.max-threads=
# HTTP POST内容最大长度，默认不限制
server.tomcat.max-http-post-size=
```



### 随机属性值生成器

```java
my.secret=${random.value}
my.number=${random.int}
my.bignumber=${random.long}
my.uuid=${random.uuid}
my.number.less.than.ten=${random.int(10)}
my.number.in.range=${random.int[1024,65536]}
```

### 系统读取应用的配置

（1）Environment类

```java
@Autowired
private Environment env;
public int getServerPort() {
   return env.getProperty("server.port", Integer.class);
}
```

（2）@Value

（3）@ConfigurationProperties

如果有一组相关联的配置属性，比如说下面这样的：

server.port=9090

server.context-path=/actuator

都是server打头的，那么可以一次性将一组配置属性读取到一个配置管理类中

```java
@ConfigurationProperties("server")
@Configuration
@Data
public class ServerConfig {
   private int port;
   private String contextPath;

}
```

 

### spring boot的自动装配

@Configuration和@Bean



## 单元测试

### spring-boot-starter-test

spring boot对单元测试提供了很好的支持，只要依赖spring-boot-starter-test即可，这个依赖会自动导入单元测试需要的所有依赖，包括了JUnit、AssertJ、Mockito、Hamcrest以及其他的一些包。

（1）JUnit：最经典的单元测试框架

（2）Spring Test、Spring Boot Test：是spring和spring boot环境下，对测试的一个支持

（3）AssertJ：是用来进行断言的

（4）Hamcrest：是用来进行复杂断言，复杂的表达式

（5）Mockito：测试替身的模拟

（6）JSONassert：都是对json数据进行操作的

（7）JsonPath



### mvc请求模拟

 

模拟GET请求：mvc.perform(get("/employee/{id}", 1))

模拟POST请求：mvc.perform(post("/employee/{id}", 1))

模拟文件上传：mvc.perform(multipart("/upload").file("file", "文件内容".getBytes("UTF-8")))

模拟表单请求：mvc.perform(post("/employee").param("name", "张三").param("age", 20))

模拟session：mvc.perform(get("/employee").sessionAttr(name, value))

模拟cookiei：mvc.perform(get("/employee").cookie(new Cookie(name ,value)))

模拟HTTP body内容，比如json：mvc.perform(get("/employee").content(json))

模拟设置HTTP header：

 ```java
mvc.perform(get("/employee/{id}"), employeeId)
	.contentType("application/x-www-form-urlencoded")
	.accept("application/json")
	.header(header, value)
 ```



比较mvc请求返回结果

```java
mvc.perform(get("/employee"))
	.andExpect(status().isOk()) // 响应是否为200状态码
	.andExpect(content().contentType(MediaType.APPLICATION_JSON)) // 返回的content type是不是application/json
	.andExpect(jsonPath("$.name").value("张三")) // 返回内容本身检查
```



```json
mvc.perform(post("/employee"))
	.andExpect(model().attribute("name", "张三"))
	.andExpect(model().attributeExists("name"))
```





## 部署

### jar方式来部署

1. 如果要以jar方式来部署spring boot应用，只需要加入下面的插件即可

```xml
<build>
	<plugins>
		<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<executable>true</executable>
				</configuration>
		</plugin>
   </plugins>
</build>
```

2. 接着只要执行：mvn package，即可完成打包，这是一个fatjar包，包含了所有依赖，可以执行的一个jar包

接着可以将jar包通过scp命令上传到服务器上

执行jar包启动系统：java -jar target\actuator-1.0.0.jar



### war包方式来部署

一般来说，实际去部署的时候，都不会是以jar包方式部署的，因为那是用的spring boot内嵌的tomcat服务器来部署的，一般还是以war包方式，部署到线上已有的tomcat容器中的。

咱们阶段一，先用spring boot内嵌容器去部署的方式，上线，因为一开始假设的是这个系统几乎就没什么用人，所以哪怕是默认的配置，其实支撑少量的请求，都是没什么问题的

但是到了阶段二，就是已经开始有一定的访问量了，那么那个时候开始，我们就会正经使用tomcat去部署，而且会深入的学习tomcat，内核

 

1. 此时要做的第一件事情，就是将spring boot应用的pom.xml中的packaging从jar修改为war

 ```xml
<groupId>com.zhss.springboot</groupId>
<artifactId>actuator</artifactId>
<packaging>war</packaging>
 ```

2. 接着需要将spring boot内嵌的tomcat修改为provided范围

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-tomcat</artifactId>
   <scope>provided</scope>
</dependency>
```

3. 接着要再次修改Application类

```java
// 这里必须继承SpringBootServletInitializer基类
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	// 这里必须实现一个configure方法
	@Override
	protected SpringAppliciationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

4. 然后再次使用mvn package打包，此时会打成一个war包，然后将war包放到tomcat的webapps目录中，启动tomcat，即可完成系统的部署



### 多环境部署

springBoot可以在src/main/resources目录下，放多个application-{profile}.properties文件

然后在启动的时候，使用java -jar -Dspring.profiles.active=prod target\actuator-1.0.0.jar来指定要激活哪个环境的profile

在生产环境中只支持properties一个配置文件的方式不够灵活，使用maven





## actuator 监控

```
http://localhost:8080/actuator

// 查看系统的基本健康信息：系统是否正常，磁盘空间，数据库
http://localhost:8080/actuator/health

// 查看最近的一些http请求
http://localhost:8080/actuator/trace

// 在线查看日志
http://localhost:8080/actuator/logfile

// 在线查看线程栈信息
http://localhost:8080/actuator/dump

// 在线查看内存快照
http://localhost:8080/actuator/heapdump

// 查看系统的性能指标：内存占用、垃圾回收次数
http://localhost:8080/actuator/metrics

// 查看系统的一些额外信息
http://localhost:8080/actuator/info

// 在线查看日志配置
http://localhost:8080/actuator/loggers

// 查看URL映射
http://localhost:8080/actuator/mappings

// 查看所有的spring bean
http://localhost:8080/actuator/beans

// 查看环境变量
http://localhost:8080/actuator/env

// 查看configpropss环境变量
http://localhost:8080/actuator/configprops

// 查看auto config的一些信息
http://localhost:8080/actuator/autoconfig
```