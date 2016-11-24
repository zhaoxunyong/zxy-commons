zxy-commons maven统一依赖管理
===============
##### 统一在父项目中引入：
	<dependencyManagement>
		<dependencies>
			<dependency>
	          <groupId>com.zxy</groupId>
	          <artifactId>zxy-commons-dependency</artifactId>
	          <version>${zxy_commons_dependency_version}</version>
	          <type>pom</type>
	          <scope>import</scope>
	        </dependency>
		</dependencies>
	</dependencyManagement>
##### 子项目只需要这样引入即可(比如需要引入lang依赖)：
	<dependency>
		<groupId>com.zxy</groupId>
		<artifactId>zxy-commons-lang</artifactId>
	</dependency>
	
##### 这样能做到所有的公共组件版本在zxy-commons-dependency项目中统一管理。
