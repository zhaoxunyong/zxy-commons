# zxy-commons-net
常用网络传输模块。目前暂时只实现了FTP的功能。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-net</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
#### FTP
```java
	public void ftp() {
        String host = "";
        int port = 21;
        String username = "";
        String password = "";
        int connectTimeoutMs = 5000;
//        long keepAliveTimeoutSeconds = 30L;
//        FTPClientConfig ftpClientConfig = new FTPClientConfig();
        FtpConfig ftpConfig = FtpConfig.builder(host)
                .port(port)
                .username(username)
                .password(password)
                .connectTimeoutMs(connectTimeoutMs)
//                .keepAliveTimeoutSeconds(keepAliveTimeoutSeconds)
//                .ftpClientConfig(ftpClientConfig)
                .build();
        FtpUtils.ftpHandle(ftpConfig, client -> {
            
        });
    }
```

#### FTPS
```java
@Test
    public void ftps() {
        String host = "";
        int port = 21;
        String username = "";
        String password = "";
        int connectTimeoutMs = 5000;
        FtpConfig ftpConfig = FtpConfig.builder(host)
                .port(port)
                .username(username)
                .password(password)
                .connectTimeoutMs(connectTimeoutMs)
                .isFtps(true)
                .build();
        FtpUtils.ftpHandle(ftpConfig, client -> {

        });
    }
```

#### SFTP
```java
	public void sftp() {
        String host = "172.28.3.71";
        int port = 22;
        String username = "";
        String password = "";
        int connectTimeoutMs = 5000;
        SftpConfig sftpConfig = SftpConfig.builder(host)
                .port(port)
                .username(username)
                .password(password)
                .connectTimeoutMs(connectTimeoutMs)
                .build();
        FtpUtils.sftpHandle(sftpConfig, client -> {
            try {
                System.out.println(client.ls("/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
```

请参考[DEMO](src/test/java/com/zxy/commons/net)
