# mars-dynamic-config

#### 介绍
##### mars-dynamic-config 包含两种配置
##### 第一种是对 properties、yml 的动态配置修改功能,节省了我们频繁部署上线去修改 properties 的值。
##### 第二种是对一条或多条数据的配置系统,主要是针对数据量小的表集中通过 mars-dynamic-config配置,节省了我们创建小表带来的工作量;通过mars-dynamic-config 配置可以直接通过集成api 直接获取对应的数据，从而节省了我们的开发时间。


#### 软件架构
软件架构说明
1.  后端使用技术 ：springboot mybatis jwt
2.  前端使用：thymeleaf 模板引擎
3.  数据库 ：mysql
4.   jdk   ：1.8
5. 【客户端】 使用框架spring、springboot、springcloud 系统

#### 安装教程

1.  先创建数据库mars_db ,导入 sql目录下的 init.sql 文件
2.  修改 mars-console-2.0.1.jar 包中的配置文件 \BOOT-INF\classes\application.properties 中的mysql 配置，及端口，使用命令 java -jar mars-console-2.0.1.jar 启动
3.  访问地址：ip + port  账户：mars 密码：mars  权限：超级管理员
4.  mars-console-2.0.1.jar 可以配置化集群模式
```properties
#【mars-console】集群配置
mars.cluster.address=ip:port,ip2:port,ip3:port
#【mars-console】同步其他服务器重试次数（默认3）
mars.cluster.sync.retry=3
```


#### 使用说明



#### 1、【客户端】 引入jar 包, 分为spring、springboot|springcloud 各自支持的jar
```xml
<!-- spring 引入 -->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>system-dynamic-config</artifactId>
    <version>2.0.1</version>
</dependency>

<!-- springboot or springcloud 引入 -->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>system-dynamic-springboot-starter</artifactId>
    <version>2.0.1</version>
</dependency>
```

#### 2、 【客户端】properties 配置

|配置参数|配置说明|是否必填|
|---|---|---|
|mars.dynamic.system.app-code|应用名称|必填|
|mars.dynamic.system.env-code|环境code|必填|
|mars.dynamic.system.server-address|server地址多个逗号分隔|必填|
|mars.dynamic.system.listen-long-poll-ms|客户端轮训毫秒数(默认30000)|否|
|mars.dynamic.system.enable-local-cache|是否开启本地缓存默认false|否|
|mars.dynamic.system.local-cache-path|本地缓存路径(默认user.home)|否|


#### 3、【客户端】spring 需要@EnableMarsConfig注解启动（springboot/springcloud 不需要） 
```java
package com.github.fashionbrot;

import com.github.fashionbrot.config.annotation.EnableSystemDynamicConfig;
import org.springframework.stereotype.Component;

@Component
@EnableSystemDynamicConfig(appCode = "app",envCode = "beta",serverAddress = "localhost:8081")
public class SystemConfig {


}
```

#### 4、使用系统配置
###### (1)、【后端系统】应用环境管理 菜单 创建 应用、环境
###### (2)、【后端系统】配置管理 菜单 创建配置 点击发布，依赖 system-dynamic-springboot-starter 就会收到服务端修改内容
###### (3)、【客户端】通过 @MarsValue 获取动态配置的值 如同spring @Value 功能 autoRefreshed 表示是否自动更新当前值
```java
    @MarsValue(value = "${abc}",autoRefreshed = true)
    private String abc;
``` 
###### (4)、【客户端】通过@MarsConfigurationProperties 注解把对应配置映射到 TestConfig 类中 <br/> 如springboot @ConfigurationProperties 功能相似 @MarsProperty 读取配置key  @MarsIgnoreField忽略abc字段配置
```java
import MarsConfigurationProperties;
import MarsIgnoreField;
import MarsProperty;
import lombok.Data;
@Data
//aaa 对应【后端系统】里面的 文件名称
@MarsConfigurationProperties(fileName = "aaa",autoRefreshed = true)
public class TestConfig {
    //修改字段的绑定名称
    @MarsProperty("abc")
    public String name ;
    //忽略字段的绑定
    @MarsIgnoreField
    private String abc;
}
```
###### (5)、通过 @MarsConfigListener 监听文件变化，可根据需要使用
```java
    /**
     * 方式三根据 配置发生变化获取到监听
     * @param
     */
    @MarsConfigListener(fileName = "test",autoRefreshed = true)
    public void testP(Properties properties){
        System.out.println("11111:"+properties.toString());
    }
```


### mars 数据动态配置 介绍

####  [软件架构及软件架构说明](#软件架构及软件架构说明)如上
####  [安装教程](#安装教程)如上

#### 使用说明
#### 1、【客户端】 引入jar 包, 分为spring、springboot|springcloud 各自支持的jar
```xml
<!-- spring 引入 -->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>data-dynamic-config</artifactId>
    <version>0.2.0</version>
</dependency>

<!-- springboot or springcloud 引入 -->
<dependency>
    <groupId>com.github.fashionbrot</groupId>
    <artifactId>data-dynamic-springboot-starter</artifactId>
    <version>0.2.0</version>
</dependency>
```
#### 2、 【客户端】properties 配置

|配置参数|配置说明|是否必填|
|---|---|---|
|mars.dynamic.data.app-code|应用code|必填|
|mars.dynamic.data.env-code|环境code|必填|
|mars.dynamic.data.server-address|server地址多个逗号分隔|必填|
|mars.dynamic.data.listen-long-poll-ms|客户端轮训毫秒数（默认10000）|否|


#### 3、【客户端】spring 需要@EnableMarsValue注解启动（springboot|springcloud 不需要） 
```java
package com.github.fashionbrot.config;


import com.github.fashionbrot.annotation.EnableDataDynamicConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDataDynamicConfig(appCode = "app",envCode = "beta",serverAddress = "192.168.1.15:8081")
public class DataDynamicConfig {


}

```


#### 4、使用系统配置
###### (1)、【后端系统】应用环境管理 菜单 创建 应用、环境
###### (2)、【后端系统】模板管理 创建模板(好比创建一个java类)、创建好模板在创建模板属性（如java类的 属性）
###### (3)、【后端系统】配置数据管理  新建就可以创建一条数据
###### (4)、【客户端】可配置获取的数据持久化到客户端的Class 类型
```java
//需要继承 MarsTemplateKeyMapping 类实现  initTemplateKeyClass 方法
package com.github.fashionbrot.config;

import com.github.fashionbrot.MarsTemplateKeyMapping;
import com.github.fashionbrot.model.BannerModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateKeyMapping extends MarsTemplateKeyMapping {

    @Override
    public Map<String, Class> initTemplateKeyClass() {
        Map<String,Class> map=new HashMap<>();
        map.put("banner", BannerModel.class);
        return map;
    }
}
```
###### (5)【客户端】客户端获取 数据2中方式
```java

    @RequestMapping("test")
    @ResponseBody
    public Object test(){
        return DataDynamicCache.getTemplateObject("banner");
    }

    @RequestMapping("test1")
    @ResponseBody
    public Object test1(){
        return DataDynamicCache.getTemplateObject("banner");
    }

    @RequestMapping("test2")
    @ResponseBody
    public Object test2(){
        List<BannerModel> banner = DataDynamicCache.getDeepTemplateObject("banner");
        if (CollectionUtil.isNotEmpty(banner)){
            for (BannerModel b :banner){
                b.setTitle("abc");
            }
        }
        return banner;
    }


```



#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
