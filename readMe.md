Use my code, please note the source, thank you.            
 Author engwen            
 Email xiachanzou@outlook.com            
 Time 2018/07/16
 
Thank JetBrains Community Support Team for its support to this project, which provides IntelliJ idea tools 
for my faster development and iteration.

 <url>https://www.jetbrains.com/?From=owlMagicComment

#  To simple principles   至简原则

### use the jar,you can get like this
    
        @RequestMapping("/signin")
        public MsgResultVO signin(User user) {
            MsgResultVO result = new MsgResultVO();
            if(null==user.getPassword || "" == user.getPassword ){
                result.errorMsg("The password must not be empty.");                \ \      @RequestMapping("/signin")
            } else if(null==user.getAccount || ""==user.getAccount ){      -------- \ \     @OwlCheckParams(notNull={"account","password"})
                result.errorMsg("Account can not be empty.");              -------- / /     public MsgResultVO signin(User user) {
            } else {                                                               / /           return userService.signin(user);
                result = userService.signin(user);                                          }
            }
            return result;
        }
   
###  and so on

#  En

##  How To Use?

* Package name
com.owl.comment.annotations
* The way of reference is

```
<dependency>
    <groupId>com.github.engwen</groupId>
    <artifactId>owlMagicComment</artifactId>
    <version>*.*.*</version>
</dependency>
```

This package relies on my other project, the OwlMagicUtil package, and the return object, MsgResultVO, is available at https://github.com/engwen/owlMagicUtil.

The spring spring spring MVC project needs to add the following configuration in the configuration file of the spring MVC Servlet

    <context: component-scan base-package="com.owl.comment.annotations"/>
    <aop: aspectj-autoproxy/>

SpringBook users need to configure scans on project startup classes

    @ComponentScan (base Packages = {"***", "com. owl", "***"})

En details ,please click <url>https://github.com/engwen/owlMagicComment/blob/master/readMeEn.md

-----
#  Cn

## 如何使用？
#### 

* 包名
com.owl.comment.annotations
* 引用方式为
```
<dependency>
    <groupId>com.github.engwen</groupId>
    <artifactId>owlMagicComment</artifactId>
    <version>*.*.*</version>
</dependency>
```

本包依赖于我的另一个项目OwlMagicUtil包，返回对象 MsgResultVO 请参考 https://github.com/engwen/owlMagicUtil

spring springMVC 项目需要在  spring mvc servlet 的配置文件中添加以下配置

     <context:component-scan base-package="com.owl.comment.annotations"/>
     <aop:aspectj-autoproxy/>
    
 SpringBoot 用户需要在 项目启动类上配置扫描
 
    @ComponentScan(basePackages = { "***","com.owl" ,"***"})




中文详情，请点击 ：<url>https://github.com/engwen/owlMagicComment/blob/master/readMeCn.md
