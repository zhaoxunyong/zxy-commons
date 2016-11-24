#zxy-commons-email
邮件发送模块。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-email</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### 使用说明
#### 代码：
```java
String subject = "This is a test email.";
String htmlBody = "This is a test emails.";
Map<String, String> properties = null;
String from = "zhaoxunyong@163.com";
List<String> toList = Lists.newArrayList("zhaoxunyong@163.com");
List<String> ccList = null;
List<String> bccList = null;
MailMessageUtils.sendMail(subject, htmlBody, properties, from, toList, ccList, bccList);
```
#### 配置文件：smtp.properties
	#注意：冒号后面必须带空格，否则加载配置文件报错
	#SMTP地址
	smtp_host: smtp.163.com
	#SMTP端口
	smtp_port: 25
	
	#验证帐号
	smtp_username: zhaoxunyong@163.com
	#验证密码
	smtp_password: 
