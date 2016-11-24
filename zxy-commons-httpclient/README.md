# zxy-commons-httpclient
Http client工具模块，包括文件下载，post，get等方法。
##### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-httpclient</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### 使用说明
```java
public class HttpclientTest {
    private final static String TEST_FILE = "/tmp/test.png";
    /**
     * setUp
     */
    @Before
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void setUp() {
    }

    /**
     * tearDown
     */
    @After
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void tearDown() {
        FileUtils.deleteQuietly(new File(TEST_FILE));
    }

    @Test
    public void download() throws ClientProtocolException, IOException {
        String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        HttpclientUtils.download(url, TEST_FILE);
        Assert.assertEquals(true, new File(TEST_FILE).exists());
    }
    
    @Test
    public void get() throws ClientProtocolException, IOException {
        String url = "http://findbugs.sourceforge.net/manual/filter.html";
        String result = HttpclientUtils.get(url);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void post() throws ClientProtocolException, IOException {
        String url = "http://findbugs.sourceforge.net/manual/filter.html";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id", "123456"));
        String result = HttpclientUtils.post(url, params);
        Assert.assertNotNull(result);
    }
}
```
