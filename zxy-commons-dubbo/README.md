# zxy-commons-dubbo
dubbo的一些常用依赖。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-dubbo</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
具体请参考[dubbo使用手册](http://dubbo.io/User+Guide-zh.htm)

### dobbo trace使用说明
可能通过traceId追踪整个dubbo的调用链.
具体使用如下:
###### 需要添加spring xml配置：
	<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
		<property name="proxyTargetClass">
			<value>true</value>
		</property>
		<property name="interceptorNames">
			<list>
				<idref bean="dubboTraceInterceptor" />
			</list>
		</property>
		<property name="beanNames">
			<list>
				<value>*Api</value>
				<value>*ApiImpl</value>
			</list>
		</property>
	</bean>

    <bean id="dubboTraceInterceptor" class="com.zxy.commons.dubbo.TraceInterceptor" />


###### 需要添加log4j.properties配置：
	log4j.additivity.com.zxy.commons.dubbo.TraceInterceptor=false
    log4j.logger.com.zxy.commons.dubbo.TraceInterceptor=TRACE, STDOUT, TRACE_LOGFILE

    #TRACE LOG
    log4j.appender.TRACE_LOGFILE=org.apache.log4j.DailyRollingFileAppender
    log4j.appender.TRACE_LOGFILE.File=${profile.log4j.root.path}/qn/qn-trace.log
    #log4j.appender.TRACE_LOGFILE.Threshold=ERROR
    log4j.appender.TRACE_LOGFILE.DatePattern=yyyy-MM-dd
    log4j.appender.TRACE_LOGFILE.Append=TRUE
    log4j.appender.TRACE_LOGFILE.layout=org.apache.log4j.PatternLayout
    log4j.appender.TRACE_LOGFILE.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss} %p [%c]\:%L Line - %m%n

