# zxy-commons-json
json处理模块，包括Object/Collection/Map与String的互转。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-json</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
#### 具体请参考JsonUtils相关方法
```java
	@Test
    public void maps2json() throws IOException {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("0", 11L);
        map.put("1-10", 22L);
        map.put("11-30", 20L);
        map.put("31-100", 25L);
        map.put("100以上", 15L);
        String mapString = JsonUtils.toJson(map);
        System.out.println("mapString:"+mapString);
        Assert.assertNotNull(mapString);
    }
    
    @Test
    public void json2map() throws IOException {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("0", 11L);
        map.put("1-10", 22L);
        map.put("11-30", 20L);
        map.put("31-100", 25L);
        map.put("100以上", 15L);
        String mapString = JsonUtils.toJson(map);
        
        Map<String, Long> maps = JsonUtils.toMap(mapString, String.class, Long.class);
        for(Map.Entry<String, Long> value : maps.entrySet()) {
            System.out.println("key: "+value.getKey()+", value: "+value.getValue());
        }
        Assert.assertNotNull(maps);
    }
```

请参考[DEMO](src/test/java/com/zxy/commons/json)
