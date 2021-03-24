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

注意的是这里的版本和jdk的版本,jdk的版本一定要在1.8不能太高

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

注意唯一也是最重要的问题需要设置jdk的版本为1.8

这里可以使用插件的方式如上面pom文件中的plugin,也可以通过idea的配置

按以下步骤即可

![image-20210324103038541](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103045.png)

点击项目结构

1. ![image-20210324103102174](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103102.png)

选择语言级别为8

点击项目

![image-20210324103217807](https://tests-1305221371.cos.ap-nanjing.myqcloud.com/20210324103217.png)

把这里面都改成8就行



检查这里的jdk是否为有1.8版本

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