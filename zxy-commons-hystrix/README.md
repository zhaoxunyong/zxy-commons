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
```html
当依赖zxy-commons-hystrix后，该模块会自动配置。配置在hystrix.properties中配置，配置如下：
#是否启动hystrix功能，默认true
hystrix.stream=true
#是否启动hystrix stream功能，默认true
hystrix.stream.enabled=true

#http://blog.csdn.net/heyutao007/article/details/51006694
#超时时间. 默认值：1000
#在THREAD模式下，达到超时时间，可以中断. 在SEMAPHORE模式下，会等待执行完成后，再去判断是否超时
#设置标准：有retry，99meantime+avg meantime. 没有retry，99.5meantime
execution.isolation.thread.timeoutInMilliseconds=1000

#配置线程池大小,默认值10个. 不配置为cpu * 2
#建议值:请求高峰时99.5%的平均响应时间 + 向上预留一些即可 
coreSize=10
#配置线程值等待队列长度,默认值:-1
#如果设置成-1，就会使用SynchronizeQueue。如果其他正整数就会使用LinkedBlockingQueue。
#建议值:-1表示不等待直接拒绝,测试表明线程池使用直接决绝策略+ 合适大小的非回缩线程池效率最高.所以不建议修改此值。 
#当使用非回缩线程池时，queueSizeRejectionThreshold,keepAliveTimeMinutes 参数无效 
maxQueueSize=-1
#线程存存活时间。默认1分钟
keepAliveTimeMinutes=1
#设置拒绝请求的临界值。只有maxQueueSize为-1时才有效。
#设置设个值的原因是maxQueueSize值运行时不能改变，我们可以通过修改这个变量动态修改允许排队的长度。默认5
queueSizeRejectionThreshold=5

#是否打开超时. 默认值：true
execution.timeout.enabled=true
#当超时的时候是否中断(interrupt) HystrixCommand.run()执行
execution.isolation.thread.interruptOnTimeout=true

#是否开启熔断，默认true
circuitBreaker.enabled=true
#10秒钟内至少20此请求失败，熔断器才发挥起作用. 默认值：20
#如果这个值是20，一个滑动窗口内只有19个请求时，即使19个请求都失败了也不会触发熔断。
circuitBreaker.requestVolumeThreshold=20
#熔断器中断请求10秒后会进入半打开状态,放部分流量过去重试. 默认值：5000ms
circuitBreaker.sleepWindowInMilliseconds=5000
#失败率达到多少百分比后熔断. 默认值：50% 主要根据依赖重要性进行调整
circuitBreaker.errorThresholdPercentage=50
#是否强制关闭熔断 如果是强依赖，应该设置为true
circuitBreaker.forceClosed=false

#设置是否缓存请求，request-scope内缓存。默认true
requestCache.enabled=true
#设置HystrixCommand执行和事件是否打印到HystrixRequestLog中
requestLog.enabled=true

#设置滑动统计的桶数量。默认10。metrics.rollingStats.timeInMilliseconds必须能被这个值整除。
metrics.rollingStats.numBuckets=10
#设置滑动窗口的统计时间。熔断器使用这个时间。默认10s
metrics.rollingStats.timeInMilliseconds=1000
#设置执行时间是否被跟踪，并且计算各个百分比，50%,90%等的时间。默认true。
metrics.rollingPercentile.enabled=true
```



### hystrix stream的访问地址：
```html
访问路径为：http://ip:port/hystrix.steam
```

### hystrix stream web界面的依赖说明
由于有些项目是spring mvc，有些项目是纯后台java服务，为了保证依赖的最小化，没有自动进行以下功能的依赖管理。
如果需要启动以下服务的话，请按照以下方式手动添加依赖：
#### 基于spring mvc：
需要手动添加以下依赖：
```xml
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-web</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

#### 基于jetty:
需要手动添加以下依赖：
```xml
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-jetty</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```