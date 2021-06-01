## `RestTemplate`

可以代替`HttpClient`来连接其他程序露出的接口,在`SpringBoot`中使用可以直接使用`RestTemplateBuild`,因为有自动注入比较方便

下面是例子

```java
@RestController
public class GetMyTb {
    @Resource
    public RestTemplateBuilder restTemplateBuilder;

    private String url="http://localhost:8080/payment/";

    @GetMapping("/get")
    public ResultMap InsertMyTb(MyTb myTb){
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.postForObject(url+"insertMyTb",myTb,ResultMap.class);
    }
    @GetMapping("/select")
    public ResultMap SelectMyTb(Integer mId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(url+"selectMyTb",ResultMap.class,mId);
    }
}

```

> 首先通过`restTemplateBuilder.build();`来创建`RestTemplate`对象然后通过`RestTemplate`对象就可以使用`getFor…`或者`postFor…`方法获取数据.
>
> 例如`getForObject(String url, Class<T> responseType, Object... uriVariables)`方法有三个参数
>
> 下面会详解三个参数
>
> 1. `url`:代表要连接接口的`url`.
> 2. `responseType`代表接口返回参数的类型,这个类型不一定是本来的类型只要满足原本的setter和getter方法就行
> 3. `uriVariables`就是`args`代表传到接口的参数
>
> 再比如`postForObject(String url, @Nullable Object request, Class<T> responseType,Object... uriVariables)`方法也是3个参数
>
> 1. `url`:和`getForObject`方法的`url`参数相同
> 2. `request`:代表返回的请求体,就是post接口的数据参数
> 3. `responseType`:和`getForObject`的`responseType`也一样.
> 4. `uriVariables`就是`args`代表传到接口的参数

也可以使用`@Bean`注册`RestTemplate`

```java
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextApi {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
    
```
推荐使用方法1,当然方法2在后面负载均衡的时候有作用.

## `eureka`

**配置**:

`jdk`的版本一定要在9版本以下不然会产生很多的bug

1. `application.yml`

   ```yml
   server:
     port: 7001
   eureka:
     client:
       register-with-eureka: false 
       fetch-registry: false #是否将eureka注册写不写都是一样的但是建议将其注册
       service-url:
        defaultZone: http://localhost:7001/eureka/
   ```

2. `pom.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>cn.edu.nuaa</groupId>
       <artifactId>eureka-demo</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <packaging>jar</packaging>
   
       <name>eureka-demo</name>
       <description>eureka-demo</description>
   
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.0.3.RELEASE</version>
           <relativePath/> <!-- lookup parent from repository -->
       </parent>
   
       <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
           <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
           <java.version>1.8</java.version>
           <spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
       </properties>
   
       <dependencies>
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
           </dependency>
       </dependencies>
   
       <dependencyManagement>
           <dependencies>
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-dependencies</artifactId>
                   <version>${spring-cloud.version}</version>
                   <type>pom</type>
                   <scope>import</scope>
               </dependency>
           </dependencies>
       </dependencyManagement>
   
       <build>
           <plugins>
               <plugin>
                   <artifactId>maven-compiler-plugin</artifactId>
                   <version>3.8.0</version>
                   <configuration> <!--让编译器使用本地的1.8版本jdk-->
                       <source>8</source>
                       <target>8</target>
                   </configuration>
               </plugin>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   
   </project>
   ```

注意的是这里的版本和`jdk`的版本,`jdk`的版本一定要在1.8不能太高

3. application.java

   ```java
   /**
    * @author 靖鸿宣
    * @since 2021/3/24
    */
   @EnableEurekaServer//启动eureka服务
   @SpringBootApplication
   public class Application {
   
       public static void main(String[] args) {
           SpringApplication.run(Application.class, args);
       }
   }
   ```

然后就可以顺利启动

注意唯一也是最重要的问题需要设置`jdk`的版本为1.8

这里可以使用插件的方式如上面pom文件中的plugin,也可以通过idea的配置

按以下步骤即可

![image-20210324103038541](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103045.png)

点击项目结构

1. ![image-20210324103102174](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103102.png)

选择语言级别为8

点击项目

![image-20210324103217807](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103217.png)

把这里面都改成8就行



检查这里的`jdk`是否为有1.8版本

![image-20210324103140386](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103140.png)

打开设置

![image-20210324103252627](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103252.png)

### error

```log
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'sessionFactory' defined in URL [jndi:/localhost/MailServerV2/WEB-INF/classes/spring-config/spring-db-applicationContext.xml]: Unsatisfied dependency expressed through bean property 'eventListeners': : Error creating bean with name 'transactionManager' defined in URL [jndi:/localhost/MailServerV2/WEB-INF/classes/spring-config/spring-db-applicationContext.xml]: Cannot resolve reference to bean 'sessionFactory' while setting bean property 'sessionFactory'; nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'sessionFactory': FactoryBean which is currently in creation returned null from getObject; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'transactionManager' defined in URL [jndi:/localhost/MailServerV2/WEB-INF/classes/spring-config/spring-db-applicationContext.xml]: Cannot resolve reference to bean 'sessionFactory' while setting bean property 'sessionFactory'; nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'sessionFactory': FactoryBean which is currently in creation returned null from getObject
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireByType(AbstractAutowireCapableBeanFactory.java:1141)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1034)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:511)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:450)
        at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:289)
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222)
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:286)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:188)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:526)
        at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:730)
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:387)
        at org.springframework.web.context.ContextLoader.createWebApplicationContext(ContextLoader.java:270)
        at org.springframework.web.context.ContextLoader.initWebApplicationContext(ContextLoader.java:197)
        at org.springframework.web.context.ContextLoaderListener.contextInitialized(ContextLoaderListener.java:47)
        at org.apache.catalina.core.StandardContext.listenerStart(StandardContext.java:3934)
        at org.apache.catalina.core.StandardContext.start(StandardContext.java:4429)
        at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:791)
        at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:771)
        at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:526)
        at org.apache.catalina.startup.HostConfig.deployDescriptor(HostConfig.java:630)
        at org.apache.catalina.startup.HostConfig.deployDescriptors(HostConfig.java:556)
        at org.apache.catalina.startup.HostConfig.deployApps(HostConfig.java:491)
        at org.apache.catalina.startup.HostConfig.start(HostConfig.java:1206)
        at org.apache.catalina.startup.HostConfig.lifecycleEvent(HostConfig.java:314)
        at org.apache.catalina.util.LifecycleSupport.fireLifecycleEvent(LifecycleSupport.java:119)
        at org.apache.catalina.core.ContainerBase.start(ContainerBase.java:1053)
        at org.apache.catalina.core.StandardHost.start(StandardHost.java:722)
        at org.apache.catalina.core.ContainerBase.start(ContainerBase.java:1045)
        at org.apache.catalina.core.StandardEngine.start(StandardEngine.java:443)
        at org.apache.catalina.core.StandardService.start(StandardService.java:516)
        at org.apache.catalina.core.StandardServer.start(StandardServer.java:710)
        at org.apache.catalina.startup.Catalina.start(Catalina.java:583)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
        at java.lang.reflect.Method.invoke(Method.java:597)
        at org.apache.catalina.startup.Bootstrap.start(Bootstrap.java:288)
        at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:413)
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'transactionManager' defined in URL [jndi:/localhost/MailServerV2/WEB-INF/classes/spring-config/spring-db-applicationContext.xml]: Cannot resolve reference to bean 'sessionFactory' while setting bean property 'sessionFactory'; nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'sessionFactory': FactoryBean which is currently in creation returned null from getObject
        at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:328)
        at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveValueIfNecessary(BeanDefinitionValueResolver.java:106)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyPropertyValues(AbstractAutowireCapableBeanFactory.java:1299)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1061)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:511)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:450)
        at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:289)
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222)
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:286)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:188)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.findAutowireCandidates(DefaultListableBeanFactory.java:807)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:735)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:666)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireByType(AbstractAutowireCapableBeanFactory.java:1126)
        ... 37 more
Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'sessionFactory': FactoryBean which is currently in creation returned null from getObject
        at org.springframework.beans.factory.support.FactoryBeanRegistrySupport.doGetObjectFromFactoryBean(FactoryBeanRegistrySupport.java:157)
        at org.springframework.beans.factory.support.FactoryBeanRegistrySupport.getObjectFromFactoryBean(FactoryBeanRegistrySupport.java:109)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getObjectForBeanInstance(AbstractBeanFactory.java:1397)
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:243)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:188)
        at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:322)
        ... 50 more
```

如果遇到如下警告更改`@SpringBootApplication`如下即可

```java
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})//此处的意思是springboot注入的时候不对这个类进行注入
@EnableEurekaServer//启动Eureka服务
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
```

也可以选择高版本的H版本的eureka

pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>test</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>payment</module>
        <module>coustomer</module>
        <module>euraka</module>
    </modules>

    <properties>
        <druid.verision>1.1.22</druid.verision>
        <springboot.verision>2.0.2.RELEASE</springboot.verision>
        <junit.verision>5.7.0-RC1</junit.verision>
        <lombok.verision>1.18.8</lombok.verision>
        <mybatis-springboot.verision>2.0.1</mybatis-springboot.verision>
        <httpclient.verision>4.5.12</httpclient.verision>
        <mysql.verision>8.0.19</mysql.verision>
        <mavencompiler.verision>3.8.0</mavencompiler.verision>
        <hibernate.verision>5.4.10.Final</hibernate.verision>
        <spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
    </properties>
<dependencyManagement>
    <dependencies>

        <dependency>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>${mavencompiler.verision}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>${druid.verision}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>${mavencompiler.verision}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.verision}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>${hibernate.verision}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.verision}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.verision}</version>
            <scope>provided</scope>
        </dependency>
        <!--mybatis-spring适配器-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-springboot.verision}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>${httpclient.verision}</version>
        </dependency>
        <!--spring boot 2.2.2-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.2.2.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Hoxton.SR8</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
    <build>
        <pluginManagement>
        <plugins>
            <plugin>
                <!--Mybatis-generator插件,用于自动生成Mapper和POJO-->
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <!--配置文件的位置-->
                    <configurationFile>src/main/resources/generatorConfig.xml</configurationFile>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
                <executions>
                    <execution>
                        <id>Generate MyBatis Artifacts</id>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>${mysql.verision}</version>
                    </dependency>
                    <!--生成代码插件-->
                    <dependency>
                        <groupId>org.mybatis.generator</groupId>
                        <artifactId>mybatis-generator-core</artifactId>
                        <version>1.3.7</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
        </pluginManagement>
    </build>
    <repositories>
        <repository>
            <id>companyname.lib1</id>
            <url>http://download.companyname.org/maven2/lib1</url>
        </repository>
        <repository>
            <id>companyname.lib2</id>
            <url>http://download.companyname.org/maven2/lib2</url>
        </repository>
        <repository>
            <id>alimaven</id>
            <name>aliyun maven</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>jcenter</id>
            <url>https://jcenter.bintray.com/</url>
        </repository>
        <repository>
            <id>bintray</id>
            <url>https://dl.bintray.com/kotlin/kotlin-eap</url>
        </repository>
    </repositories>

</project>
```

然后在子组件中使用即可

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>test</artifactId>
        <groupId>org.example</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>euraka</artifactId>

    <properties>
        <maven.compiler.source>14</maven.compiler.source>
        <maven.compiler.target>14</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration> <!--让编译器使用本地的13版本jdk-->
                    <source>14</source>
                    <target>14</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

接着建造启动类

```java
@SpringBootApplication
@EnableEurekaServer
public class EurakaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurakaApplication.class,args);
    }
}
```

> 更改版本后发现不需要使用`@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})`也可以启动(不太理解,可能是玄学)

启动然后点击服务检查,发现运行成功

![image-20210324135313394](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324135320.png)

这东西坑还是很多的例如版本的问题要求很多并且使用不同的版本会报不同的异常比较难以理解.建议按照文件中的配置原封不动使用.

然后启动eureka客户端端

首先添加依赖项

```xml
<!--这里是加载要注册进eureka的服务的pom文件中-->	
	<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	</dependency>
```

在启动类中增加注释

```java
@EnableEurekaClient
```

先运行服务端再运行客户端即可完成

![image-20210324191954925](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324192002.png)

创建eureka的集群

原理:互相注册也就是把其他eureka当成注册者

首先由于单机测试为了能更好的分别具体是哪个EurakaApplication所以增加配置文件

修改C:\WINDOWS\System32\drivers\etc\hosts

增加

```txt
127.0.0.1 eureka7001.com
127.0.0.1 eureka7002.com
```

可能由于权限问题无法更改

更改方法是先在桌面创建hosts文件然后再把文件拖入上面的文件夹中即可.

然后修改`application.yml`文件

```yml
server:
  port: 7001
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false #是否将eureka注册写不写都是一样的但是建议将其注册
    service-url:
      defaultZone: http://eureka7002.com:7002/eureka/
  instance:
    hostname:  eureka7001.com #eureka的服务端名字
```

```yml
server:
  port: 7002
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false #是否将eureka注册写不写都是一样的但是建议将其注册
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
  instance:
    hostname:  eureka7002.com #eureka的服务端名字
```

然后启动eureka即可

注意观察截图中的两个地方

![image-20210324203118403](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324203118.png)

![image-20210324203136336](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324203136.png)

发现分别是7001和7002满足配置则集群成功

然后修改eureka注册服务即可

```yml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true #是否将eureka注册写不写都是一样的但是建议将其注册
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
```

### 注册的集群

1. 首先是保证两个集群的服务注册时使用相同的名字

即`application.yml`中的此属性一致,注意`name`的值可以随意设定,由于集群的设定尽量保证两个服务的一致性方便后续开发,本机调试时记得更改端口号

```yml
spring:
 application:
  name: cloud-payment-service
```

然后在80端也就是浏览器端调用服务

首先创建`RestTemplate`bean这里时spring的知识点,@Configuration和Beans.xml一样,@Bean和bean标签一致

```java
@Configuration
public class ApplicationContextApi {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

>  值得注意的是这里不能使用`springboot`有的自动注入的`RestTemplateBuilder`因为无法使用`@LoadBalanced`标签下面会讲解这个标签的作用.

**`@LoadBalanced`**

这个代表`RestTemplate`的负载均衡(负载均衡的意思就是因为已经有集群服务了所以要满足浏览器端不再只使用一个服务,只使用一个服务那集群的意义就没有了.使用负载均衡后发现服务会被随机访问,减轻了服务端的压力),使用后会加载一个`Filter`然后更改上面说到的`spring-application-name`变成`ip:config`的格式,然后正常访问即可.

然后只需要改变`url="http://CLOUD-PAYMENT-SERVICE/ "`(这里的CLOUD-PAYMENT-SERVICE为你上面配置的`spring-application-name`会自动的转换为`ip:cnfig`的格式).

其他操作不变即可完成服务注册的集群

## `zookeeper`

首先安装zookeeper

1. 安装jvm

   直接使用linux命令安装即可

   ```cmd
    deb jdk-version
   ```

2. 然后下载zookeeper

   http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.6.2/apache-zookeeper-3.6.2-bin.tar.gz

   下载后传输到`linux`服务器上然后使用`tar -zxvf apache-zookeeper-3.6.2-bin.tar.gz`

   然后使用`cd apache-zookeeper-3.6.2-bin`

   再`cd conf`

   然后使用`cp zoo_sample.cfg zoo.fig`

   然后修改zoo.fig

   `vim zoo.fig`

   输入:w开始写入文件

   ![img](https://img-blog.csdn.net/20160920231956375?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

   更改如下即可

   使用:wq保存

   然后打开bin目录运行./zkServer.sh即可

`application.yml`

```yml
server:
  port: 8084
spring:
  application:
    name: cloud-provider-8084
  cloud:
    zookeeper:
      connect-string: 8.136.225.205:2181 #zookeeper的运行ip和环境
```

## `fegin`

首先是`fegin`的作用他是类似于`mybatis`对我们的接口进行了一定程度上的封装,让我们可以直接通过方法调用接口,而不用通过`HttpClient`等请求接口(也就是把这一过程封装了).

使用`fegin`

1. 首先配置pom文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <parent>
           <artifactId>test</artifactId>
           <groupId>org.example</groupId>
           <version>1.0-SNAPSHOT</version>
       </parent>
       <modelVersion>4.0.0</modelVersion>
   
       <artifactId>cusfeign</artifactId>
   
       <properties>
           <maven.compiler.source>14</maven.compiler.source>
           <maven.compiler.target>14</maven.compiler.target>
       </properties>
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-actuator</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-openfeign</artifactId>
       </dependency>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
       </dependency>
   </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <artifactId>maven-compiler-plugin</artifactId>
                   <configuration> <!--让编译器使用本地的13版本jdk-->
                       <source>14</source>
                       <target>14</target>
                   </configuration>
               </plugin>
           </plugins>
       </build>
   </project>
   ```

2. 然后配置`applciation.yml`文件

   ```yml
   server:
     port: 80
   spring:
     application:
       name: cusfeign-80
   eureka:
     client:
       register-with-eureka: false
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
   ```

3. 然后在启动类中添加@EnableFeginClients注解让他能启动Fegin的服务

   ```java
   @SpringBootApplication
   @EnableFeignClients
   public class FeginApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(FeginApplication.class, args);
       }
   
   }
   ```

4. 然后在service层配置使用`@FeginClient`注解配置

   ```java
   @FeignClient("CLOUD-PAYMENT-SERVICE")
   public interface FeginService {
       @GetMapping("/payment/selectMyTb")
       public ResultMap SelectMyTb(@RequestParam("mId") Integer mId);
   }
   
   ```

   值得说明的是@FeignClient中间的值代表你服务的名字下面的方法代表的你要实现的接口的名字.然后记得传参记得使用@RequestParm和@RequestBody等注解,因为这东西不支持这类东西的自动注入可能会抛一个异常.

   ```java
   org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' not supported
           at org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.handleNoMatch(RequestMappingInfoHandlerMapping.java:201)
           at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.lookupHandlerMethod(AbstractHandlerMethodMapping.java:420)
           at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:366)
           at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:66)
           at org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler(AbstractHandlerMapping.java:404)
           at org.springframework.web.servlet.DispatcherServlet.getHandler(DispatcherServlet.java:1233)
           at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1016)
           at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)
           at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
           at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
           at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
           at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
           at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at com.github.xiaoymin.knife4j.spring.filter.ProductionSecurityFilter.doFilter(ProductionSecurityFilter.java:53)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at com.github.xiaoymin.knife4j.spring.filter.SecurityBasicAuthFilter.doFilter(SecurityBasicAuthFilter.java:90)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)
           at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)
           at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:94)
           at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)
           at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
           at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
           at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
           at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)
           at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
           at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:541)
           at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)
           at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)
           at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
           at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
           at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:367)
           at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)
           at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:860)
           at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1598)
           at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
           at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
           at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
           at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
           at java.lang.Thread.run(Thread.java:748)
   ```

5. 然后直接调用即可

   ```java
   @RestController
   @Slf4j
   public class WebTest {
       @Resource
       FeginService service;
   
       @GetMapping("/cou/selectMyTb")
       public ResultMap SelectMyTb(@RequestParam("mId") Integer mId){
           return service.SelectMyTb(mId);
       }
   }
   ```


## `Hystrix`

1. 在pom.xml中添加

   ```xml
   <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
   </dependency>
   ```

2. main中添加

   ```java
   @EnableHystrix
   ```

3. 在方法中使用在`@HystrixCommand`标记如果发生异常则会跳到`FailBackFail`方法

   ```java
   @Service
   public class TestServices {
       public String getIt(Integer id){
           return "success"+Thread.currentThread().getName()+" "+id;
       }
       @HystrixCommand(fallbackMethod =  "FallBackFail",commandProperties = {
               @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value="3000")
       })
       public String Timeout(Integer id){
           int time=1/0;
           try {
               TimeUnit.SECONDS.sleep(time);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return "timeout"+id+" "+Thread.currentThread().getName();
       }
       public String FallBackFail(Integer id){
           return "running fail service callback fail"+" "+id;
       }
   }
   ```

## `HandlerInterceptor`

使用方式:

```java
package com.demo.Filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 靖鸿宣
 * @since 2021/3/27
 */
public class test implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

```



```java
@Configuration
public class config implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new test()).addPathPatterns("/**").excludePathPatterns("/css/**");
    }
}

```

## `config`

1. 配置服务端(获取远程`github`配置)

   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-actuator</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-config-server</artifactId>
       </dependency>
   </dependencies>
   ```

2. 配置yml文件

   ```yml
   server:
     port: 3344
   spring:
     application:
       name: cloud-config
     cloud:
       config:
         server:
           git:
            strict-host-key-checking: false
            clone-on-start: true
            uri: https://github.com/iosyyy/config.git
            search-paths:
               - config
            username: 626797813@qq.com
            password: jinghong12345..
         label: master
   eureka:
     client:
      service-url:
       defaultZone: http://eureka7001.com:7001/eureka/
   ```

   值得注意的是这里不要开启代理否则会出问题

3. 然后创建启动类

   ```java
   @EnableConfigServer
   @SpringBootApplication
   public class ConfigApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(ConfigApplication.class, args);
       }
   
   }
   ```

4. 启动后运行http://127.0.0.1:3344/master/config-dev.yml

   注意这里的名字是必须是******-******.yml,-前面代表name后面代表profile(这东西的官方规范),并且不要使用代理

5. 然后创建服务端使用配置

   增加依赖

   ```xml
   <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-config</artifactId>
   </dependency>
   ```

6. 创建bootstrap.yml(系统级别的配置文件)对application.yml有覆盖作用

   ```yml
   server:
     port: 3345
   spring:
     application:
       name: config-client
     cloud:
       config:
         name: config #上面的name
         label: master #上面的label
         profile: dev #上面的profile
         uri: http://127.0.0.1:3344/
   eureka:
     client:
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka/
   management:
     endpoints:
       web:
         exposure:
           include: "*"
   
   ```

7. 创建启动类

   ```java
   package com.config.client;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
   
   /**
    * @author 靖鸿宣
    * @since 2021/4/8
    */
   @SpringBootApplication
   @EnableEurekaClient
   public class ConfigClientApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(ConfigClientApplication.class, args);
       }
   
   }
   
   ```

8. 创建web类

   ```java
   @RestController
   @RefreshScope
   public class WebTest {
   
       @Value("${config.info}")
       private String word;
   
       @GetMapping("/getit")
       public String getIt(){
           return word;
       }
   }
   ```

   运行后发现启动成功

## 使用nacos做负载均衡和配置管理

1. 首先是下载nacos服务器

   点击以下链接即可下载

   https://github.com/alibaba/nacos/tags

2. 下载后在cmd中运行startup.cmd即可

3. 如果启动成功以后运行页面 http://10.50.71.100:8848/nacos/index.html

   如果显示如下则结果正确

   ![image-20210418114205943](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210418114213.png)

   注意进入此页面之前需要先登录默认的账号和密码都是nacos

4. 一些常见错误

   * 如果提示JAVA_HOME找不到jdk可能是jdk的版本问题,建议使用1.8以上的版本如果不能解决则,右键startup.cmd文件,选择用文本编辑器打开(这里选什么都行).更改

     ```cmd
     if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
     ```

     为,注意JAVA_HOME是帮你找到本地的jdk路径用的

     ```cmd
     set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_281\
     if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
     
     ```

   * 如果还是启动报错则修改MODE为单机模式

     ```cmd
     set MODE="standalone"
     ```

   

然后配置服务进入nacos

直接修改配置文件即可

```yaml
spring:
  application:
   name: nacos-provider
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
server:
  port: 9092
management:
  server:
    port: 9092
  endpoints:
    web:
      exposure:
        include: '*'
```

这里的spring.cloud.nacos.discovery.server-addr是代表nacos服务器的地址

然后在启动类中添加@EnableDiscoveryClient注解即可完成服务配置

负载均衡和上面的类似这里不做过多说明

然后使用nacos代替config

首先创建bootstrap.yml文件配置如下这里的spring.application.name一定要配置后面有用

```yml
server:
  port: 90

spring:
  application:
    name: nacos-config
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
```

然后创建application.yml文件

```yml
spring:
  profiles:
    active: dev
```

然后在nacos中添加配置文件

这里的格式是${spring.application.name}-\${spring.profiles.active}.yaml

也就是这里的![image-20210418114934394](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210418114934.png)Data ID要设置成上面格式的名字

然后直接读取即可成功,注意这里配置了nacos所以要加上@EnableDiscoveryClient注解

