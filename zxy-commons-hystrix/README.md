# zxy-commons-hystrix
hystrix依赖模块。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-hystrix</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
#### 启动hystrix服务:
```java
new HystrixServer().startServer(8086);
```
```html
访问路径为：http://ip:8086/hystrix.steam
```
