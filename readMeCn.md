# OwlMagicComment
 
#### 

* 包名
com.owl.comment
* 引用方式为
```
<dependency>
    <groupId>com.github.engwen</groupId>
    <artifactId>owlMagicComment</artifactId>
    <version>1.0</version>
</dependency>
```

本包依赖于我的另一个项目OwlMagicUtil包，返回对象 MsgResultVO 请参考 https://github.com/engwen/owlMagicUtil

spring springMVC 项目需要在  spring mvc servlet 的配置文件中添加以下配置

    <context:component-scan base-package="com.owl.comment">
      <!--  <context:include-filter type="annotation" expression="org.aspectj.lang.annotation.Aspect"/>-->
    </context:component-scan>
    <aop:aspectj-autoproxy/>
    <!-- 启动AspectJ支持   只对扫描过的bean有效-->
    <!--Aop配置-->
    <aop:config proxy-target-class="false"> </aop:config>
    
 SpringBoot 用户需要在 项目启动类上配置扫描
 
    @ComponentScan(basePackages = { "***","com.owl" ,"***"})

-------
> 自定义注解

1. @OwlCheckParams

     该注解用于controller层校验请求参数，包含notAllNull（不可全部为空），notNull（不能为空）以及canNull（可以为空）三个属性，为方便书写接口文档以及后期查询代码，此三个属性合并起来应当为本接口可接收的所有参数，notAllNull中的参数不能全部为null，否则该接口将返回 "请求参数 \*\* 不能全为空"，notNull中的参数不能为空，否则该接口将返回 "请求参数 \*\* 不能为空"，canNull中的参数可以为空

     例如：

    原始代码：
            
        @RequestMapping("/signin")
        public MsgResultVO signin(User user) {
            MsgResultVO result = new MsgResultVO();
            if(null==user.getPassword || "" == user.getPassword ){
                result.errorMsg("密码不能为空");
            } else if(null==user.getAccount || ""==user.getAccount ){
                result.errorMsg("账号不能为空");
            } else {
                result = userService.signin(user);
            }
            return result;
        }
        
      使用注解后代码:

        @RequestMapping("/signin")
        @OwlCheckParams(notNull={"account","password"})
        public MsgResultVO signin(User user) {
            return userService.signin(user);
        }
      返回数据：{"result":false,"resultCode":"0002","resultMsg":"请求参数 \[account,password\] 不能为空","resultData":null,"params":{}}
    
    节省编写判断代码的时间
    
1. @OwlLogInfo
    
    该注解将会在使用他的地方打印"now in class %s , method %s" ，注意，这并不能给你一些额外的有用信息，只是用来输出一些日志帮助你快速定位被调用的接口。
    
           
本包引入的jar包包括
 
```
        <dependency>
               <groupId>com.github.engwen</groupId>
               <artifactId>owlMagicUtil</artifactId>
               <version>2.3</version>
        </dependency>
        <!--aop 核心依赖以及完成本功能所需依赖 -->
       <dependency>
                  <groupId>javax</groupId>
                  <artifactId>javaee-api</artifactId>
                  <version>7.0</version>
              </dependency>
      
              <dependency>
                  <groupId>org.springframework</groupId>
                  <artifactId>spring-web</artifactId>
                  <version>4.2.5.RELEASE</version>
              </dependency>
      
              <dependency>
                  <groupId>org.aspectj</groupId>
                  <artifactId>aspectjrt</artifactId>
                  <version>1.9.1</version>
              </dependency>
      
              <!-- 添加日志相关jar包 -->
              <dependency>
                  <groupId>log4j</groupId>
                  <artifactId>log4j</artifactId>
                  <version>1.2.17</version>
              </dependency>
```