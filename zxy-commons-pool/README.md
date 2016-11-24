# zxy-commons-pool
连接池模块。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-pool</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### 使用说明
##### 实现自己的pool对象，并且不能让外部直接引用：
```java
    class TestPool extends ObjectPool<PoolObj> {

	    /**
	     * @param factory
	     */
	    public TestPool() {
	        super(new TestFactory(), new GenericObjectPoolConfig());
	    }
	
	    /**
	     * @param factory
	     * @param config
	     */
	    public TestPool(GenericObjectPoolConfig config) {
	        super(new TestFactory(), config);
	    }
	    
	    private static class TestFactory extends BasePooledObjectFactory<PoolObj>{
	        @Override
	        public PoolObj create() {
	            PoolObj poolObj = new PoolObj();
	            poolObj.setObj("obj1");
	            return poolObj;
	        }
	
	        @Override
	        public PooledObject<PoolObj> wrap(PoolObj poolObj) {
	            return new DefaultPooledObject<PoolObj>(poolObj);
	        }
	
	        @Override
	        public boolean validateObject(PooledObject<PoolObj> pooledObject) {
	            if (pooledObject != null) {
	                PoolObj channel = pooledObject.getObject();
	                return channel != null;
	            }
	            return false;
	        }
	
	        @Override
	        public void destroyObject(PooledObject<PoolObj> pooledObject) {
	            if (pooledObject != null) {
	//                PoolObj poolObj = pooledObject.getObject();
	//                poolObj.close();
	            }
	        }
	    }
	}
```

##### 编写ObjectPoolFactory对象:
```java
	// 注意ObjectPoolFactory对象需要单例，并且不能让外部直接引用
	class TestObjectPoolFactory {
	    private final ObjectPool<PoolObj> pool;
	    
	    private final static class TestObjectPoolFactoryBuilder {
	        private final static TestObjectPoolFactory BUILDER = new TestObjectPoolFactory();
	    }
	    
	    public TestObjectPoolFactory() {
	        this.pool = new TestPool();
	    }
	    
	    public static TestObjectPoolFactory getInstance() {
	        return TestObjectPoolFactoryBuilder.BUILDER;
	    }
	    
	    /**
	     * 从池中获取pool对象
	     * 
	     * @return Pool object
	     */
	    public PoolObj get() {
	        return pool.getResource();
	    }
	  
	    /**
	     * 将pool对象放回到池中
	     * 
	     * @param resource pool对象
	     */
	    public void close(PoolObj resource) {
	        if (resource != null) {
	            pool.returnResource(resource);
	        }
	    }
	  
	    /**
	     * 将pool对象放回到池中
	     * 
	     * @param resource pool对象
	     */
	    public void closeBroken(PoolObj resource) {
	        if (resource != null) {
	            pool.returnBrokenResource(resource);
	        }
	    }
	}
```

##### 编写executer对象:
```java
    // 注意executer对象需要单例
	public class TestExecuter extends AbstractPoolExecuter<PoolObj> {
	    private final TestObjectPoolFactory factory = TestObjectPoolFactory.getInstance();
	    
	    private static final class TestExecuterBuilder {
	        private static final TestExecuter INSTANCE = new TestExecuter();
	    }
	
	    public static final TestExecuter getInstance() {
	        return TestExecuterBuilder.INSTANCE;
	    }
	    
	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected PoolObj get() {
	        return factory.get();
	    }
	
	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected void close(PoolObj poolObj) {
	        factory.close(poolObj);
	    }
	
	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public void closeBroken(PoolObj poolObj) {
	        factory.close(poolObj);
	    }
	
	}
```

##### 使用:
```java
	TestExecuter executer = TestExecuter.getInstance();
	executer.execute(new Execution<PoolObj, String>() {
            @Override
            public String execute(PoolObj poolObj) {
                System.out.println("poolObj===" + poolObj);
                assertEquals("obj1", poolObj.getObj());
                return poolObj.getObj();
            }

        });
```
        
请参考[DEMO](src/test/java/com/zxy/commons/pool)
