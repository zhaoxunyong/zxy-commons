# zxy-commons-logger
日志系统的一些常用依赖。目前采用log4j实现, 后续也可能变更为其他的日志系统。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-logger</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 动态log文件的使用说明
- log4j.properties配置
```xml
    #LOG_FILE
    log4j.appender.LOGFILE=com.zxy.commons.logger.appender.DailyRollingFileAppender
    log4j.appender.LOGFILE.File=/var/log/zxy/${AppId}/${ModuleId}.log
    #log4j.appender.LOGFILE.Threshold=ERROR
    log4j.appender.LOGFILE.DatePattern=yyyy-MM-dd
    log4j.appender.LOGFILE.Append=TRUE
    log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout
    log4j.appender.LOGFILE.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss} %p [%c]\:%L Line - %m%n


    #ROLLING_FILE
    log4j.appender.ROLLING_FILE=com.zxy.commons.logger.appender.RollingFileAppender
    log4j.appender.ROLLING_FILE.File=/var/log/zxy/${AppId}/${ModuleId}.log
    #log4j.appender.ROLLING_FILE.Threshold=ERROR
    log4j.appender.ROLLING_FILE.Append=TRUE
    log4j.appender.ROLLING_FILE.MaxFileSize=10MB
    log4j.appender.ROLLING_FILE.MaxBackupIndex=50
    log4j.appender.ROLLING_FILE.layout=org.apache.log4j.PatternLayout
    log4j.appender.ROLLING_FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss} %p [%c]\:%L Line - %m%n
```

- 启动:
```xml
    在启动脚本加入参数: -DAppId=project -DModuleId=module, log文件就会根据传入的参数动态生成.
```

#### 目前支持的动态参数有:
- AppId
- ModuleId
- TaskName
