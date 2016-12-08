# zxy-commons-lang
常用工具模块，包括集合、对象、字符串、id生成等功能，并对commons-lang与guava部分功能进行了二次封装。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-lang</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
#### IdUtils
#### id生成类
基于twitter-snowflake生成的long型id，[具体可参考](http://www.lanindex.com/twitter-snowflake%EF%BC%8C64%E4%BD%8D%E8%87%AA%E5%A2%9Eid%E7%AE%97%E6%B3%95%E8%AF%A6%E8%A7%A3/)
```java
IdUtils.genLongId();
```

生成String类型id，[具体可参考](src/main/java/com/yyfq/commons/lang/idgenerator/UUIDUtils.java)
```java
IdUtils.genLongId();
```

#### BytesUtils
用于实现各种基本类型与byte数据之间的转换

#### CIDRUtils
ICDR转换工具

#### CollectionsUtils
Collection工具类

#### DatesUtils
日期工具类
```java
 1. 当前日期转yyyy-MM-dd HH:mm:ss格式字符串：
    DatesUtils.YYMMDDHHMMSS.toString();
 2. yyyy-MM-dd HH:mm:ss格式字符串转日期：
    DatesUtils.YYMMDDHHMMSS.toDate("2016-06-21 13:00:00");
```

#### IntsUtils
int工具类

#### KryoSeriizationUtils
Kryo序列化与反序列化

#### MapsUtils
Map工具类

#### ObjectsUtils
对象工具类

#### RegexUtils
正则表达式工具类

#### StringUtils
String工具类

#### CommonConstant
公共常量，包括：
```java
	/**
     * 用于系统中需要分隔的特殊字符，默认值：{@value}
     */
    public static final String SPECIAL_CHAR = "ゆ";
    
    /**
     * 系统默认分隔符，默认值：{@value}
     */
    public static final String DEFAULT_SPLIT = ",";

    /**
     * SESSION上下文变量
     */
    public final static String SESSION_CONTEXT = "session_context";
```

#### ErrorCodeEnum
公共错误枚举对象，包括：
```java
	/**
     * 业务处理异常
     */
    BUSINESS_ERROR(900, "业务处理异常"), 
    
    
    /**
     * 参数传值异常
     */
    PARAMETER_ERROR(901, "参数传值异常"), 
    
    /**
     * 数据存储异常
     */
    DATA_ACCESS_ERROR(902, "数据存储异常"), 
    
    /**
     * 未知异常
     */
    UNKNOWN_ERROR(903, "未知异常"), 
    
    /**
     * 找不到数据
     */
    NOT_FOUND_ERROR(904, "找不到数据");
```

#### exception
包括：BaseException/BusinessException/DatasAccessException/NotFoundException/ParameterException/UnknowException

#### 公共dto与model对象
baseDto：
```java
	public abstract class BaseDto implements Serializable {
	    private static final long serialVersionUID = -9207608685373174069L;
	
	    @Override
	    public String toString() {
	        return ObjectsUtils.toString(this);
	    }
	}
```

baseModel:
```java
	public abstract class BaseModel implements Serializable {
	    private static final long serialVersionUID = -5981694241742540962L;
	    
	    @Override
	    public String toString() {
	        return ObjectsUtils.toString(this);
	    }
	}
```

