# yyfq-commons-poi
POI工具类，暂时只实现对excel的操作。
##### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.yyfq</groupId>
    <artifactId>yyfq-commons-poi</artifactId>
    <version>${yyfq_commons_version}</version>
</dependency>
```
	
### 使用说明
#### 导出excel:
ExcelUtils.export(data, outputPath);
```html
data为：
1. Multimap：
LinkedHashMultimap<String, String> multimap = LinkedHashMultimap.create();
// key为column， value为cell
multimap.put("栏位1", "a1");
multimap.put("栏位1", "a2");
multimap.put("栏位1", "a3");

multimap.put("栏位2", "b1");
multimap.put("栏位2", "b2");
multimap.put("栏位2", "b3");

2. Table：
Table<Integer, String, String> table = TreeBasedTable.create();
// rowKey为row index， columnKey为column，value为cell
// rowKey必须从1开始
table.put(1, "栏位1", "a1");
table.put(1, "栏位2", "b1");

table.put(2, "栏位1", "a2");
table.put(2, "栏位2", "b2");

table.put(3, "栏位1", "a3");
table.put(3, "栏位2", "b3");

Multimap与Table对象可以由具体的业务对象转换而成。
```

#### 导入excel: 
```html
1. 将excel转换成Multimap：
ExcelUtils.read2map(inputPath);

2. 将excel转换成Table：
ExcelUtils.read2table(inputPath);

Multimap与Table对象可以转换为具体的业务对象。
```

请参考[DEMO](src/test/java/com/zxy/commons/poi/excel)