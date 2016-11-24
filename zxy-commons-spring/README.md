# zxy-commons-spring
spring基础包，包括spring的一些常用依赖。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-spring</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### 非依赖注入的方式调用spring对象：
#### 必须在spring配置中加入：
```xml
	<bean id="springBeanUtils" class="com.zxy.commons.spring.SpringBeanUtils" />
```

#### 或者引入commons-spring.xml：
##### commons-spring.xml已经打包到zxy-commons-spring.jar中：
```xml
	<import resource="classpath:commons-spring.xml" />
```

#### 调用：
```java
	UserService userService = SpringBeanUtils.getBean(UserService.class);
```
### 注意
#### SpringBeanUtils一般用于非依赖注入的方式调用spring对象，建议尽量通过spring的依赖注入加载对象
