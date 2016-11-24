# zxy-commons-mybatis
spring+mybatis基础操作模块，包括数据库读写分离与缓存。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-mybatis</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### 使用说明
#### spring配置：
- 扫描com.zxy.commons包的注解：
```xml
	<context:component-scan base-package="com.zxy.commons">
    </context:component-scan>
```

- 激活自动代理功能：
```xml
	<aop:aspectj-autoproxy proxy-target-class="true" />
```

- 配置datasource:
```xml
	<bean id="parentDataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close" abstract="true">
		<!-- 基本属性 url、user、password -->
		<property name="driverClassName" value="${jdbc.driver}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
        <property name="proxyFilters">
            <list>
                <ref bean="stat-filter"/>
            </list>
        </property>
		<!-- 连接池最大使用连接数量 -->
		<property name="maxActive" value="${jdbc.maxActive}" />
		<!-- 初始化连接大小 -->
		<property name="initialSize" value="${jdbc.initialSize}" />
		<!-- 获取连接最大等待时间 -->
		<property name="maxWait" value="${jdbc.maxWait}" />
        <!-- 连接池最小空闲 -->
		<property name="minIdle" value="${jdbc.minIdle}" />
		<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
		<property name="timeBetweenEvictionRunsMillis" value="${jdbc.timeBetweenEvictionRunsMillis}" />
		<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
		<property name="minEvictableIdleTimeMillis" value="${jdbc.minEvictableIdleTimeMillis}" />
		<property name="validationQuery" value="${jdbc.validationQuery}" />
		<property name="testWhileIdle" value="${jdbc.testWhileIdle}" />
		<property name="testOnBorrow" value="${jdbc.testOnBorrow}" />
		<property name="testOnReturn" value="${jdbc.testOnReturn}" />
		<!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->
		<property name="poolPreparedStatements" value="${jdbc.poolPreparedStatements}" />
		<property name="maxPoolPreparedStatementPerConnectionSize" value="${jdbc.maxPoolPreparedStatementPerConnectionSize}" />
	</bean>
	
	<!--  主库 -->
	<bean id="masterDataSource" parent="parentDataSource">
		<property name="url" value="${jdbc.masterUrl}" />
	</bean>
 
	<!--  从库 -->
	<bean id="slaveDataSource" parent="parentDataSource">
		<property name="url" value="${jdbc.slaveUrl}" />
	</bean>
 	
  	<bean id="dataSource" class="com.zxy.commons.mybatis.ChooseDataSource">
	   	<!-- 这个targetDataSource是必须要注入的 -->
		<property name="targetDataSources">
	      	<map key-type="java.lang.String">
	         	<entry key="MASTER" value-ref="masterDataSource"/>
	         	<entry key="SLAVE" value-ref="slaveDataSource"/>
	      	</map>
	   	</property>
	   	<!-- 默认的数据源 -->
	   	<property name="defaultTargetDataSource" ref="masterDataSource"/>
	</bean>
```

#### 使用：
#### 默认读主库，如需要读从库的话：
```java
	@SelectDataSource(HandleDataSource.SLAVE)
	public User getUserById(int userId) {
	   ...
	}
```
