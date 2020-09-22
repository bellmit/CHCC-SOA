* 基于Spring Cloud Greenwich | Spring Cloud Alibaba | Spring Boot 2.1 | OAuth2 的SOA微服务平台
* 使用Vue.js | Element-ui 前端框架

<br>

#### 主架构 

````
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.3.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
````

#### 模块说明

```
vue-Backstage -- 前端vue工程

sso-server -- OAuth2认证服务器

platform
|________ parent -- 核心maven依赖
|________ api -- 对外暴露接口模块
           |_____ middle -- 数据中台系统
           |_____ rest -- app暴露接口
|________ core -- 项目核心包
           |_____ pojo -- 实体类与工具类
|________ dao -- 数据持久化模块
           |_____ mybatis -- mybatis相关数据库操作
|________ service -- 微服务模块化服务
```

