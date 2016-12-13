# zxy-commons-apidocs
通过代码自动生成文档的依赖包。目前用swagger实现。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-apidocs</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>

### 使用说明
```html
当依赖zxy-commons-apidocs后，该模块会自动配置。配置在apidocs.properties中配置，配置如下： 
#是否启动自动生成文档的功能
apidocs.enabled=true
# try it out的host,不配置默认取本机ip
apidocs.host=
# try it out的port,不配置默认为80
apidocs.port=8088
# try it out的basePath
apidocs.basepath=/qn
# 文档title
apidocs.title=用户管理接口文档
# 文档的描述
apidocs.description=包括用户管理的一些常用api操作接口。
# 文档的termsOfServiceUrl
apidocs.termsOfServiceUrl=
# 文档的联系人
apidocs.contact.name=赵训勇
# 文档的联系人email
apidocs.contact.email=zhaoxunyong@qq.com
# 文档的联系人url
apidocs.contact.url=
# 文档的版本
apidocs.version=1.0.0
#排除的url，多个用逗号分隔
#apidocs.excludes=/rest.*
apidocs.excludes=

#是否启用自动邮件发送
apidocs.sendmail.enabled=true
#当执行mvn apidocs:deploy时，自动发邮件通知相关成员，多个用逗号分隔
apidocs.notifyChangeEmails=zhaoxunyong@qq.com,zhaoqinnuo@qq.com
```