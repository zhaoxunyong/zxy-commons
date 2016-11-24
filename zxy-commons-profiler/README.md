依赖说明
=======
    如果需要使用性能监控profiler功能，需要在pom.xml中加入
    <dependency>
	    <groupId>com.zxy</groupId>
	    <artifactId>zxy-commons-profiler</artifactId>
	    <version>${zxy_commons_version}</version>
    </dependency>

说明
===========
###### 需要添加spring xml配置：
	<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
		<property name="proxyTargetClass">
			<value>true</value>
		</property>
		<property name="interceptorNames">
			<list>
				<idref bean="profilerInterceptor" />
				<idref bean="printDigestInterceptor" />
			</list>
		</property>
		<property name="beanNames">
			<list>
				<value>*Api</value>
				<value>*ApiImpl</value>
				<value>*ServiceImpl</value>
				<value>*DataSource</value>
			</list>
		</property>
	</bean>  
	
	<bean id="profilerInterceptor" class="com.zxy.commons.profiler.connector.ProfilerInterceptor">
		<property name="monitorTime">
			<value>100</value>
		</property>
	</bean>
	
	<bean id="printDigestInterceptor" class="com.zxy.commons.profiler.connector.PrintDigestInterceptor">
		<property name="defaultPrintLevel" value="INFO" />
	</bean>
	

###### 需要添加log4j.properties配置：
	log4j.additivity.profiler.speed.log=false
	log4j.logger.profiler.speed.log=INFO, STDOUT, PROFILER_LOGFILE
	
	log4j.additivity.profiler.callMark.log=false
	log4j.logger.profiler.callMark.log=INFO, STDOUT, CALLMARK_LOGFILE
	
	#PROFILE LOG
	log4j.appender.PROFILER_LOGFILE=org.apache.log4j.DailyRollingFileAppender
	log4j.appender.PROFILER_LOGFILE.File=/var/log/zxy/zxy-project/zxy-project-profiler.log
	#log4j.appender.PROFILER_LOGFILE.Threshold=ERROR
	log4j.appender.PROFILER_LOGFILE.DatePattern=yyyy-MM-dd
	log4j.appender.PROFILER_LOGFILE.Append=TRUE   
	log4j.appender.PROFILER_LOGFILE.layout=org.apache.log4j.PatternLayout
	log4j.appender.PROFILER_LOGFILE.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss} %p [%c]\:%L Line - %m%n
	
	#CALLMARK LOG
	log4j.appender.CALLMARK_LOGFILE=org.apache.log4j.DailyRollingFileAppender
	log4j.appender.CALLMARK_LOGFILE.File=/var/log/zxy/zxy-project/zxy-project-callmark.log
	#log4j.appender.CALLMARK_LOGFILE.Threshold=ERROR
	log4j.appender.CALLMARK_LOGFILE.DatePattern=yyyy-MM-dd
	log4j.appender.CALLMARK_LOGFILE.Append=TRUE   
	log4j.appender.CALLMARK_LOGFILE.layout=org.apache.log4j.PatternLayout
	log4j.appender.CALLMARK_LOGFILE.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss} %p [%c]\:%L Line - %m%n

请参考[DEMO](src/test/resources)
