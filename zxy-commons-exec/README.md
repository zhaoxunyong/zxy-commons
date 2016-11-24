# zxy-commons-exec	
执行系统命令模块。
##### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-exec</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### 使用说明
#### 同步：
```java
ExecutorResult executorResult = CmdExecutor.exec("ipconfig");
```

#### 异步：
```java
CmdExecutor.execAsyc("ipconfig");
```

