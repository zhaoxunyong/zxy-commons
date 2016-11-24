# zxy-commons-mq
kafka mq基础操作模块。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-mq</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

Producer说明
===========
### 发送字符串消息到指定的topic：
	###### 可以是单独的字符串或者josn字符串
	StringProducer.send(String topic, String routerKey, String value);
    topic：    主题
    routerKey：路由key，为空时会随机写入
    value：    消息内容
    
    Example:
    long id = IdUtils.genLongId();
    StringProducer.send("test", String.valueOf(id), "This is a test");
### 发送对象消息到指定的topic：
### 如果是需要发送对象的话，统一使用ObjectProducer，比如说对象为User：
	ObjectProducer.producer4Json("test", String.valueOf(id), user, User.class);
	
### 使用：
    User user = new User();
    user.setName("zhaoxunyong");
    user.setPwd("000000");
    ObjectProducer.producer4Json("test", String.valueOf(id), user, User.class);
### producer方式有以下几种：
	producer4Kryo:Kryo序列化方式，注意：字段名有变动旧有的数据不能反序列化
	producer4Jdk:Jdk自带序列化方式
	producer4Json:jackson序列化方式
	建议：如果数据不大的话，使用producer4Json，这样字段变更会比较灵活
### 配置kafka_producer.properties
#### 请将kafka_producer.properties放到src/main/resources目录下
```java
	#kafka broker地址
	metadata.broker.list=192.168.10.10:9092,192.168.10.11:9092,192.168.10.12:9092
	#0: producer不会等待broker发送ack 
	#1: 当leader接收到消息之后发送ack 
	#2: 当所有的follower都同步消息成功后发送ack.
	request.required.acks=1
	#producer消息发送的模式,同步或异步
	#sync，单条发送
	#async，buffer一堆请求后，再一起发送，高并发时建议使用
	producer.type=async
	#在async模式下,消息在producer端buffer的条数
	batch.num.messages=200
	#在async模式下,当message被缓存的时间超过此值后,将会批量发送给broker 此值和batch.num.messages协同工作
	queue.buffering.max.ms=5000
```


Consumer说明
===========
### 订阅消息，如果是字符串的话，可以直接使用StringConsumers：
    StringConsumer.createConsumer(GROUP_ID, TOPIC).subscribe(new ConsumerCallback<String>() {
        @Override
        public void process(String topic, long offset, int partition, String message) {
            LOGGER.info("message=========="+message);
        }

        @Override
        public void exceptions(String topic, long offset, int partition, String message, Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    });
### 如果消费的是对象的话，统一通过ObjectConsumer，比如说对象为User:
	ObjectConsumer.createConsumer(GROUP_ID, TOPIC).subscribe4Json(User.class, new ConsumerCallback<User>() {
	
        @Override
        public void process(String topic, long offset, int partition, User message) {
            LOGGER.info("message name:"+message.getName());
        }
        @Override
        public void exceptions(String topic, long offset, int partition, User message, Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            
        }
    });
### subscribe方式有以下几种：
	subscribe4Kryo:Kryo反序列化方式，注意：字段名有变动旧有的数据不能反序列化
	subscribe4Jdk:Jdk反自带序列化方式
	subscribe4Json:jackson反序列化方式
	建议：如果数据不大的话，使用subscribe4Json，这样字段变更会比较灵活
	
### 配置kafka_consumer.properties
#### 请将kafka_consumer.properties放到src/main/resources目录下
```java
	#zookeeper地址
	zookeeper.connect=192.168.10.10:2181,192.168.10.11:2181,192.168.10.12:2181
	#zookeeper session超时时间，单位：毫秒
	zookeeper.session.timeout.ms=10000
	#consumer端同zk服务器同步offset等数据的间隔时间，单位：毫秒
	zookeeper.sync.time.ms=2000
	#自动提交offset到zookeeper，如果为false，消费一条会自动提交一条到zookeeper，数据处理会更精确，但性能没有true好
	auto.commit.enable=true
	#consumer会定时地将offset写入到zookeeper上的时间，默认为1分钟，修改为1000毫秒
	auto.commit.interval.ms=1000
```

请参考[DEMO](src/test/java/com/zxy/commons/mq)
